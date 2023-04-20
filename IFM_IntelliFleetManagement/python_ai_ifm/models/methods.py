# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


import mysql.connector
import pandas as pd
from datetime import datetime
import tempfile
import io

from matplotlib import pyplot as plt
from tensorflow.keras.models import load_model


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
    sql = """
    SELECT data FROM models_weights
    WHERE model_id = %s
    ORDER BY ABS(TIMEDIFF(time, NOW()))
    LIMIT 1
    """
    my_cursor.execute(sql, (model_id,))

    model_file = io.BytesIO(my_cursor.fetchone()[0])
    with tempfile.NamedTemporaryFile(suffix=".hdf5", delete=False) as f:
        f.write(model_file.getvalue())
        temp_model_path = f.name
        model.load_weights(temp_model_path)

    mydb.close()

    return model


def model_load_structure(model_id):
    # Save the model structure in the database
    mydb = connect_to_database("obd2_models")
    my_cursor = mydb.cursor()

    # Select the file from the database
    file_name = "model{model_id}_structure.h5".format(model_id=model_id)
    sql = """
        SELECT data FROM models_structures
        WHERE model_id = %s
        ORDER BY ABS(TIMEDIFF(time, NOW()))
        LIMIT 1
    """
    my_cursor.execute(sql, (model_id,))

    model_file = io.BytesIO(my_cursor.fetchone()[0])
    with tempfile.NamedTemporaryFile(suffix=".h5", delete=False) as f:
        f.write(model_file.getvalue())
        temp_model_path = f.name
        model = load_model(temp_model_path)

    mydb.close()

    return model


def model_load_weights_backup(model, model_id):
    model_weights_path = "../generated/backup/model{model_id}_weights.hdf5".format(model_id=model_id)
    model.load_weights(model_weights_path)

    return model


def model_load_structure_backup(model_id):
    model_structure_path = "../generated/backup/model{model_id}_structure.h5".format(model_id=model_id)
    model = load_model(model_structure_path)

    return model


def get_time():
    # Get the current date and time
    now = datetime.now()

    # Format the date and time as a string that MySQL can understand
    formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')

    return formatted_date


def model_plot(history):
    # Plot the training and validation loss and accuracy
    plt.plot(history.history['loss'], label='train_loss')
    plt.plot(history.history['val_loss'], label='val_loss')
    plt.plot(history.history['accuracy'], label='train_acc')
    plt.plot(history.history['val_accuracy'], label='val_acc')
    plt.legend()
    plt.show()
    #plt.savefig()
