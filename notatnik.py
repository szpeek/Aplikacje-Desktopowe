import tkinter as tk
from tkinter import ttk


bufor_notatki = ""

def zmien_tryb():
    if tryb_var.get() == "Normalny":
        text_area.config(state="normal")
    else:
        text_area.config(state="disabled")

def buforuj():
    global bufor_notatki

    if bufor_var.get():
        bufor_notatki = text_area.get("1.0", tk.END).strip()
        text_area.config(state="normal")
        text_area.delete("1.0", tk.END)
        text_area.insert(tk.END, "Treść została zbuforowana i ukryta.")
        text_area.config(state="disabled")
    else:
        text_area.config(state="normal")
        text_area.delete("1.0", tk.END)
        text_area.insert(tk.END, bufor_notatki)
        zmien_tryb()

root = tk.Tk()
root.title("Edytor Notatek")
root.geometry("500x400")
root.resizable(False, False)

frame = ttk.Frame(root, padding=10)
frame.pack(fill="both", expand=True)

text_area = tk.Text(frame, wrap="word", height=15)
text_area.pack(fill="both", expand=True, pady=10)
text_area.insert(tk.END, "Wprowadz tutaj swoje notatki!")

controls_frame = ttk.Frame(frame)
controls_frame.pack(fill="x", pady=5)

tryb_var = tk.StringVar(value="Normalny")

rb1 = ttk.Radiobutton(controls_frame, text="Normalny", variable=tryb_var, value="Normalny", command=zmien_tryb)
rb2 = ttk.Radiobutton(controls_frame, text="Tylko do odczytu", variable=tryb_var, value="Odczyt", command=zmien_tryb)
rb1.pack(side="left", padx=10)
rb2.pack(side="left")

bufor_var = tk.BooleanVar(value=False)
check_bufor = ttk.Checkbutton(controls_frame, text="Buforuj i ukryj notatkę", variable=bufor_var, command=buforuj)
check_bufor.pack(side="right", padx=10)

root.mainloop()
