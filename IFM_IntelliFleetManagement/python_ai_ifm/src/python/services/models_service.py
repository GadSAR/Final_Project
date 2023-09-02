from src.python.services.data_service import DataService
from datetime import datetime
import tempfile
import io
from matplotlib import pyplot as plt
from keras.models import load_model
import os


root_dir = os.path.join('src', 'resources', 'generated')


class ModelsService:
    def __init__(self):
        self.data_service = DataService()

    def model_save_structure(self, file_name, model, model_id):
        # Save the model structure locally
        file_path = root_dir + file_name
        model.save(file_path)
        # Save the model structure in the database
        my_cursor = self.data_service.mydb.cursor()
        # Open the file in binary mode and read its contents
        with open(file_path, 'rb') as file:
            file_data = file.read()
        # Insert the file contents into the database
        sql = "INSERT INTO models_structures VALUES (%s, %s, %s, %s)"
        val = (file_name, file_data, model_id, self.get_time())
        my_cursor.execute(sql, val)
        self.data_service.mydb.commit()
        print(my_cursor.rowcount, "record inserted.")

    def model_save_weights(self, file_name, model, model_id):
        # Save the model structure locally
        file_path = root_dir + file_name
        model.save_weights(file_path)
        # Save the model structure in the database
        my_cursor = self.data_service.mydb.cursor()
        # Open the file in binary mode and read its contents
        with open(file_path, 'rb') as file:
            file_data = file.read()
        # Insert the file contents into the database
        sql = "INSERT INTO models_weights VALUES (%s, %s, %s, %s)"
        val = (file_name, file_data, model_id, self.get_time())
        my_cursor.execute(sql, val)
        self.data_service.mydb.commit()
        print(my_cursor.rowcount, "record inserted.")

    def model_load_weights(self, model, model_id):
        # Save the model structure in the database
        my_cursor = self.data_service.mydb.cursor()
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
        return model

    def model_load_structure(self, model_id):
        # Save the model structure in the database
        my_cursor = self.data_service.mydb.cursor()
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
        return model

    def model_load_weights_backup(self, model, model_id):
        model_weights_path = root_dir + "/backup/model{model_id}_weights.hdf5".format(model_id=model_id)
        model.load_weights(model_weights_path)
        return model

    def model_load_structure_backup(self, model_id):
        model_structure_path = root_dir + "/backup/model{model_id}_structure.h5".format(model_id=model_id)
        model = load_model(model_structure_path)
        return model

    def get_time(self):
        # Get the current date and time
        now = datetime.now()
        # Format the date and time as a string that MySQL can understand
        formatted_date = now.strftime('%Y-%m-%d %H:%M:%S')
        return formatted_date

    def model_plot(self, history):
        # Plot the training and validation loss and accuracy
        plt.plot(history.history['loss'], label='train_loss')
        plt.plot(history.history['val_loss'], label='val_loss')
        plt.plot(history.history['accuracy'], label='train_acc')
        plt.plot(history.history['val_accuracy'], label='val_acc')
        plt.legend()
        plt.show()
