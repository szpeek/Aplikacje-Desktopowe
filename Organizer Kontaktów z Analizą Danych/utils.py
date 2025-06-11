import re
import json
from functools import reduce, partial
from datetime import datetime

def validate_email(email):
    pattern = r"^[\w\.-]+@[\w\.-]+\.\w+$"
    return re.match(pattern, email) is not None

def remove_duplicates(contacts):
    seen = set()
    unique = []
    for c in contacts:
        ident = (c["Imię"], c["Nazwisko"], c["Email"])
        if ident not in seen:
            seen.add(ident)
            unique.append(c)
    return unique

def log_action(message):
    def decorator(func):
        def wrapper(*args, **kwargs):
            with open("log.txt", "a") as f:
                f.write(f"{datetime.now()} - {message}\n")
            return func(*args, **kwargs)
        return wrapper
    return decorator

def get_analyzer():
    def analyze(data):
        emails = list(map(lambda c: c["Email"], data))
        domains = set(map(lambda e: e.split("@")[1], emails))
        return f"Unikalne domeny email: {', '.join(domains)}"
    analyze.__name__ = "analyze"
    return analyze