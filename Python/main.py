from tkinter import *
from tkinter import scrolledtext
import requests
import json
from tkinter.messagebox import showerror


def validate_json(json_data):
    try:
        json.loads(json_data)
    except ValueError as err:
        return False
    return True


def search():
    url = url_line.get()
    response = requests.get(url)
    content = response.content
    txt.delete('1.0', END)
    txt.insert(INSERT, content)


def save():
    text = txt.get('1.0', END)
    if validate_json(text):
        f = open('text.json', 'w')
        f.write(text)
    else:
        showerror(title="Error", message="It is not JSON text or something wrong!")


window = Tk()
window.title("JSON TextEditor")

window.grid_columnconfigure(1, weight=1)
window.grid_rowconfigure(1, weight=1)

lbl = Label(window, text="URL:", font=('arial bold', 11))
lbl.grid(column=0, row=0, sticky=W+E, padx=15)

url_line = Entry(window, width=10)
url_line.grid(column=1, row=0, columnspan=8, sticky=W+E+N+S)
url_line.focus()

btn_search = Button(window, text="Search", borderwidth=1, activebackground='#345', activeforeground='white', font=('arial bold', 11), command=lambda: search())
btn_search.grid(column=9, row=0, sticky=W+E, ipadx=10)

txt = scrolledtext.ScrolledText(window)
txt.grid(column=0, row=1, columnspan=10, sticky=S+E+W+N)

btn_save = Button(window, text="Save", borderwidth=1, activebackground='#345', activeforeground='white', font=('arial bold', 11), command=lambda: save())
btn_save.grid(column=0, row=2, columnspan=10, sticky=W+E+N+S)

window.mainloop()
