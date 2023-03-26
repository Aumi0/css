import datetime

def encrypt(input_string):
    # Implement your encryption algorithm here
    # For example, you could use a simple substitution cipher like ROT13:
    shift = 13
    output = ""
    for c in input_string:
        if 'a' <= c <= 'z':
            output += chr((ord(c) - ord('a') + shift) % 26 + ord('a'))
        elif 'A' <= c <= 'Z':
            output += chr((ord(c) - ord('A') + shift) % 26 + ord('A'))
        else:
            output += c
    return output

def decrypt(input_string):
    # Implement your decryption algorithm here
    shift = 13
    output = ""
    for c in input_string:
        if 'a' <= c <= 'z':
            output += chr((ord(c) - ord('a') + 26 - shift) % 26 + ord('a'))
        elif 'A' <= c <= 'Z':
            output += chr((ord(c) - ord('A') + 26 - shift) % 26 + ord('A'))
        else:
            output += c
    return output

# Load password from file
try:
    with open("password.txt", "r") as f:
        password = decrypt(f.readline().strip())
except FileNotFoundError:
    print("Password file not found. Using default password.")
    password = "mysecretpassword"

# Loop until user enters "q"
while True:
    input_string = input("Enter password: ")

    # Check if user wants to reset password
    if input_string == "55":
        password = input("Enter new password: ")
        with open("password.txt", "w") as f:
            f.write(encrypt(password) + "\n")
        continue

    # Check if password is correct
    if input_string != password:
        print("Failed.")
        continue

    try:
        # Open the file for appending
        with open("output.txt", "a") as f:
            # Loop until user enters "q" or "log"
            while True:
                # Get current date and time
                now = datetime.datetime.now()

                # Prompt user for input
                input_string = input("Enter some text (press 'q' to quit, 'log' to view decrypted file): ")

                # Check if user wants to quit
                if input_string == "q":
                    break

                # Check if user wants to view decrypted file
                if input_string == "log":
                    with open("output.txt", "r") as log_file:
                        for line in log_file:
                            print(decrypt(line.strip()))
                    continue

                # Write to file with timestamp on a new line
                f.write(encrypt(f"[{now:%Y-%m-%d %I:%M:%S %p}] {input_string}\n"))
                f.flush() # flush buffer to ensure immediate write
    except IOError as e:
        print(f"Error writing to file: {e}")

    # Exit the program
    break
