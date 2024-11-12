import mysql.connector
from mysql.connector import Error

def create_connection(host_name, user_name, user_password, db_name):
    conn = None
    try:
        conn = mysql.connector.connect(
            host=host_name,
            user=user_name,
            password=user_password
        )
        if conn.is_connected():
            print("Connected to MySQL server.")
            cursor = conn.cursor()

            cursor.execute(f"CREATE DATABASE IF NOT EXISTS {db_name}")
            print(f"Database '{db_name}' created or verified.")
            conn.close()


        conn = mysql.connector.connect(
            host=host_name,
            user=user_name,
            password=user_password,
            database=db_name
        )
        if conn.is_connected():
            print(f"Connected to the '{db_name}' database.")
    except Error as e:
        print(f"Error: {e}")
    return conn



def create_table(conn):

    try:
        sql_create_student_table = """
        CREATE TABLE IF NOT EXISTS student (
            Id INT PRIMARY KEY,
            Name VARCHAR(50),
            Email VARCHAR(100),
            Age INT,
            Year VARCHAR(30)
        );"""
        cursor = conn.cursor()
        cursor.execute(sql_create_student_table)
        print("Student table created.")
    except Error as e:
        print(f"Error: {e}")

def insert_student(conn, student):
    sql = '''INSERT INTO student (Id, Name, Email, Age, Year)
             VALUES (%s, %s, %s, %s, %s)'''
    try:
        cursor = conn.cursor()
        cursor.execute(sql, student)
        conn.commit()
        print("Student inserted successfully.")
    except Error as e:
        print(f"Error: {e}")

def select_all_students(conn):
    sql = "SELECT * FROM student"
    try:
        cursor = conn.cursor()
        cursor.execute(sql)
        rows = cursor.fetchall()
        for row in rows:
            print(row)
    except Error as e:
        print(f"Error: {e}")

def main():
    host_name = "csci-cs418-22.dhcp.bsu.edu"
    user_name = "studentdba"
    user_password = "K*hKSu%6yZ"
    db_name = "university_db"


    conn = create_connection(host_name, user_name, user_password, db_name)

    if conn is not None and conn.is_connected():
        create_table(conn)

        student1 = (1, 'Alice', 'alice@example.com', 20, 'Sophomore')
        student2 = (2, 'Bob', 'bob@example.com', 22, 'Senior')

        insert_student(conn, student1)
        insert_student(conn, student2)

        print("All students:")
        select_all_students(conn)

        conn.close()
    else:
        print("Error! Cannot create the database connection.")

if __name__ == '__main__':
    main()
