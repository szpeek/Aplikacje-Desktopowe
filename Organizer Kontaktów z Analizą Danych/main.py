import tkinter as tk
from tkinter import messagebox, filedialog
import json, re, csv
from functools import partial
from utils import *

class ContactApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Organizer Kontaktów")

        self.contacts = []

        
        self.entries = {}
        for i, field in enumerate(["Imię", "Nazwisko", "Email", "Telefon"]):
            tk.Label(root, text=field).grid(row=i, column=0)
            entry = tk.Entry(root)
            entry.grid(row=i, column=1)
            self.entries[field] = entry

        tk.Button(root, text="Dodaj kontakt", command=self.add_contact).grid(row=4, column=0, columnspan=2)


        self.listbox = tk.Listbox(root, width=50)
        self.listbox.grid(row=5, column=0, columnspan=2)

        tk.Button(root, text="Wczytaj z pliku", command=self.load_from_file).grid(row=6, column=0)
        tk.Button(root, text="Eksportuj", command=self.export_to_file).grid(row=6, column=1)
        tk.Button(root, text="Analizuj", command=self.analyze_contacts).grid(row=7, column=0, columnspan=2)

    @log_action("Dodano kontakt")
    def add_contact(self):
        contact = {k: v.get() for k, v in self.entries.items()}

        if not validate_email(contact["Email"]):
            messagebox.showerror("Błąd", "Nieprawidłowy adres email!")
            return

        self.contacts.append(contact)
        self.contacts = remove_duplicates(self.contacts)
        self.update_listbox()

    def update_listbox(self):
        self.listbox.delete(0, tk.END)
        for c in self.contacts:
            self.listbox.insert(tk.END, f"{c['Imię']} {c['Nazwisko']} | {c['Email']} | {c['Telefon']}")

    def load_from_file(self):
        try:
            filename = filedialog.askopenfilename(filetypes=[("CSV files", "*.csv")])
            with open(filename, newline='', encoding='utf-8') as f:
                reader = csv.DictReader(f)
                self.contacts.extend(reader)
                self.contacts = remove_duplicates(self.contacts)
                self.update_listbox()
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie można wczytać pliku: {e}")

    def export_to_file(self):
        try:
            filename = filedialog.asksaveasfilename(defaultextension=".json")
            with open(filename, "w", encoding='utf-8') as f:
                json.dump(self.contacts, f, indent=2, ensure_ascii=False)
            messagebox.showinfo("Sukces", "Zapisano kontakty.")
        except Exception as e:
            messagebox.showerror("Błąd", f"Nie można zapisać pliku: {e}")

    def analyze_contacts(self):
        analyze = get_analyzer()
        messagebox.showinfo("Analiza", analyze(self.contacts))

root = tk.Tk()
app = ContactApp(root)
root.mainloop()
