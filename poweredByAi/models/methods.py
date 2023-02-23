# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


import mysql
import pandas as pd


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
    model.save('../generated/' + file_name + '.h5')

    # Save the model structure in the database
    mydb = connect_to_database()
    my_cursor = mydb.cursor()

    # Open the file in binary mode and read its contents
    with open(file_name, 'rb') as file:
        file_data = file.read()

    # Insert the file contents into the database
    sql = "INSERT INTO files (name, data, id) VALUES (%s, %s, %d)"
    val = (file_name, file_data, model_id)
    my_cursor.execute(sql, val)

    mydb.commit()

    print(my_cursor.rowcount, "record inserted.")

    mydb.close()


def model_save_weights(file_name, model, model_id):
    # Save the model structure locally
    model.save_weights('../generated/' + file_name + '.hdf5')

    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Open the file in binary mode and read its contents
    with open(file_name, 'rb') as file:
        file_data = file.read()

    # Insert the file contents into the database
    sql = "INSERT INTO weights_files (name, data, id) VALUES (%s, %s , %d)"
    val = (file_name, file_data, model_id)
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
    sql = "SELECT weights_files FROM files WHERE name = %s"
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
    sql = "SELECT data FROM files WHERE name = %s"
    my_cursor.execute(sql, file_name)

    my_result = my_cursor.fetchone()

    # Write the file contents to a new file
    with open(file_name, 'wb') as file:
        file.write(my_result[0])

    model.load(file)

    mydb.close()

    return model
