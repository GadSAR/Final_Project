# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


import mysql.connector
import pandas as pd
from datetime import datetime


def connect_to_database(database_name):
    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="admin",
        database=database_name  # Replace with the name of the database you want to use
    )

    return mydb


def get_data():
    mydb = connect_to_database("obd2data")

    my_cursor = mydb.cursor()

    my_cursor.execute("SELECT * FROM obd2scannerdata")

    my_result = my_cursor.fetchall()

    mydb.close()

    df = pd.DataFrame(my_result, columns=my_cursor.column_names)

    return df


def model_save_structure(file_name, model, model_id):
    # Save the model structure locally
    file_path = '../generated/' + file_name
    model.save(file_path)

    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Open the file in binary mode and read its contents
    with open(file_path, 'rb') as file:
        file_data = file.read()

    # Insert the file contents into the database
    sql = "INSERT INTO models_structures VALUES (%s, %s, %s, %s)"
    val = (file_name, file_data, model_id, get_time())
    my_cursor.execute(sql, val)

    mydb.commit()

    print(my_cursor.rowcount, "record inserted.")

    mydb.close()


def model_save_weights(file_name, model, model_id):
    # Save the model structure locally
    file_path = '../generated/' + file_name
    model.save_weights(file_path)

    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Open the file in binary mode and read its contents
    with open(file_path, 'rb') as file:
        file_data = file.read()

    # Insert the file contents into the database
    sql = "INSERT INTO models_weights VALUES (%s, %s, %s, %s)"
    val = (file_name, file_data, model_id, get_time())
    my_cursor.execute(sql, val)

    mydb.commit()

    print(my_cursor.rowcount, "record inserted.")

    mydb.close()


def model_load_weights(model, model_id):
    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Select the file from the database
    file_name = 'model' + model_id + '_weights.hdf5'
    sql = "SELECT * FROM models_weights WHERE _id = %d ORDER BY ABS(TIMESTAMPDIFF(SECOND, time, current_time)) LIMIT 1"
    my_cursor.execute(sql, file_name)

    my_result = my_cursor.fetchone()

    # Write the file contents to a new file
    with open(file_name, 'wb') as file:
        file.write(my_result[0])

    model.load_weights(file)

    mydb.close()

    return model


def model_load_structure(model, model_id):
    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Select the file from the database
    file_name = 'model' + model_id + '_structure.h5'
    sql = "SELECT * FROM models_structures WHERE _id = %d ORDER BY ABS(TIMESTAMPDIFF(SECOND, time, current_time)) LIMIT 1"
    my_cursor.execute(sql, model_id)

    my_result = my_cursor.fetchone()

    # Write the file contents to a new file
    with open(file_name, 'wb') as file:
        file.write(my_result[0])

    model.load(file)

    mydb.close()

    return model


def get_time():
    # Get the current date and time
    now = datetime.now()

    # Format the date and time as a string that MySQL can understand
    formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')

    return formatted_date
