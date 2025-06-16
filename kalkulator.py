import tkinter as tk
from tkinter import messagebox

class SimpleCalculator:
    def __init__(self, root):
        self.root = root
        self.root.title("Prosty Kalkulator")

        self.display_var = tk.StringVar()

        self.entry = tk.Entry(root, textvariable=self.display_var, font=('Arial', 20), justify='right', state='readonly', readonlybackground='white')
        self.entry.grid(row=0, column=0, columnspan=4, sticky='nsew', padx=5, pady=5)

        self.current_expression = ""

        self.create_buttons()

        for i in range(5):
            root.grid_rowconfigure(i, weight=1)
        for j in range(4):
            root.grid_columnconfigure(j, weight=1)

    def create_buttons(self):
        buttons = [
            ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
            ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
            ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
            ('0', 4, 0), ('.', 4, 1), ('=', 4, 2), ('+', 4, 3),
            ('C', 5, 0)
        ]

        for (text, row, col) in buttons:
            action = lambda x=text: self.on_button_click(x)
            button = tk.Button(self.root, text=text, font=('Arial', 18), command=action)
            if text == 'C':
                button.grid(row=row, column=col, columnspan=4, sticky='nsew', padx=2, pady=2)
            else:
                button.grid(row=row, column=col, sticky='nsew', padx=2, pady=2)

    def on_button_click(self, char):
        if char == 'C':
            self.current_expression = ""
            self.update_display()
        elif char == '=':
            try:
                result = eval(self.current_expression)
                self.current_expression = str(result)
                self.update_display()
            except ZeroDivisionError:
                messagebox.showerror("Błąd", "Dzielenie przez zero!")
                self.current_expression = ""
                self.update_display()
            except Exception:
                messagebox.showerror("Błąd", "Nieprawidłowe wyrażenie!")
                self.current_expression = ""
                self.update_display()
        else:
            self.current_expression += str(char)
            self.update_display()

    def update_display(self):
        self.display_var.set(self.current_expression)

if __name__ == "__main__":
    root = tk.Tk()
    calc = SimpleCalculator(root)
    root.mainloop()
