#! /usr/bin/env python

from Tkinter import *
import time

class App:
    def __init__(self, master):
        frame = Frame(master)
        frame.pack()
        scale = Scale(frame, from_=0, to=180,
              orient=HORIZONTAL, command=self.update)
        scale.grid(row=0)
        pwm = Pwm()


    def update(self, angle):
        duty = float(angle) / 10.0 + 2.5

root = Tk()
root.wm_title('Servo Control')
app = App(root)
root.geometry("200x50+0+0")
root.mainloop()

