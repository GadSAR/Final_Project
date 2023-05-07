# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 2 : Predicts which issue**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import numpy as np
import pandas as pd
# For model 2
import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MultiLabelBinarizer

from src.python.services.models_service import ModelsService


class Model2:
    def __init__(self):
        self.models_service = ModelsService()
        self.model_2 = None
        self.trouble_codes = None
        self.mlb = None
        self.model2_load_structure()
        self.model2_load_weights()

    def model2(self, data, epochs):
        x2, y2 = self.model2_data(data)
        x_train2, x_test2, y_train2, y_test2 = self.model2_split_data(x2, y2)
        self.model2_train(x_train2, x_test2, y_train2, y_test2, epochs)

    def model2_check(self, data):
        x2, y2 = self.model2_data(data)
        x_train2, x_test2, y_train2, y_test2 = self.model2_split_data(x2, y2)
        self.model2_accuracy(x_test2, y_test2)
        print(y_test2)

    def model2_prediction(self, predict_data, data):
        y_pred = self.model2_predict(predict_data, data)
        return y_pred

    def model2_data(self, data):
        # Filter the dataset to include only rows where "issues" equals 1
        x2 = data[data['issues'] == 1].copy()

        # Make a y data based on X
        temp_y2 = x2['trouble_codes'].values
        temp_y2_df = pd.DataFrame(temp_y2, columns=['trouble_codes'])
        self.mlb = MultiLabelBinarizer()
        y2 = pd.DataFrame(self.mlb.fit_transform(temp_y2_df['trouble_codes'].str.split(',')), columns=self.mlb.classes_)

        # Drop the columns that we don't need
        x2.drop(['issues', 'trouble_codes', 'time', 'vehicle_id', 'id', 'ip'], axis=1, inplace=True)

        # Save the trouble codes for later use
        self.trouble_codes = y2.columns

        return x2, y2

    def model2_split_data(self, x2, y2):
        # Split the data into training and testing sets
        x_train2, x_test2, y_train2, y_test2 = train_test_split(x2, y2, test_size=0.2, random_state=42)

        return x_train2, x_test2, y_train2, y_test2

    def model2_structure(self, input_size, output_size):
        # Define the model
        self.model_2 = tf.keras.Sequential([
            tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(128, return_sequences=True),
                                          input_shape=(input_size, 1)),
            tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(64)),
            tf.keras.layers.Dropout(0.2),
            tf.keras.layers.Dense(output_size, activation='softmax')
        ])

        # Compile the model
        self.model_2.compile(loss='categorical_crossentropy', optimizer=tf.keras.optimizers.Adam(learning_rate=1e-4)
                        , metrics=['accuracy'])

        # Print the model summary
        self.model_2.summary()

        # Save model structure
        self.model2_save_structure()

    def model2_accuracy(self, x, y):
        # Evaluate the performance of the model
        loss2, accuracy2 = self.model_2.evaluate(np.expand_dims(x, axis=2), y)
        print('Accuracy:', accuracy2)

    def model2_predict(self, x_pred, data):
        x, y = self.model2_data(data)
        y_pred = self.model_2.predict(x_pred)
        y_pred = np.round(y_pred).astype(int)
        code_pred = self.trouble_codes[np.argmax(y_pred)]
        print(code_pred)

        return code_pred

    def model2_train(self, x_train2, x_test2, y_train2, y_test2, epochs):
        # Get the number of categories
        num_categories = y_train2.shape[1]

        # Build model structure
        self.model2_structure(x_train2.shape[1], num_categories)

        # Train the model
        history2 = self.model_2.fit(x_train2, y_train2, epochs=epochs, batch_size=32, verbose=1,
                                    validation_data=(x_test2, y_test2))

        # Save model weights
        self.model2_save_weights()

        # Check model accuracy
        self.model2_accuracy(x_test2, y_test2)

        self.models_service.model_plot(history2)

        y_pred = self.model_2.predict(x_test2)
        print(y_pred)

    def model2_save_structure(self):
        self.models_service.model_save_structure('model2_structure.h5', self.model_2, 2)

    def model2_save_weights(self):
        self.models_service.model_save_weights('model2_weights.hdf5', self.model_2, 2)

    def model2_load_structure(self):
        self.model_2 = self.models_service.model_load_structure_backup(2)

    def model2_load_weights(self):
        self.model_2 = self.models_service.model_load_weights_backup(self.model_2, 2)