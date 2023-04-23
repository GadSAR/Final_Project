# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 2 : Predicts which issue**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt

# For model 2
import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MultiLabelBinarizer

from models.methods import model_save_structure, model_load_structure, model_load_weights, model_save_weights, \
    model_plot, model_load_structure_backup, model_load_weights_backup

global model_2


def model2(data, epochs):
    x2, y2 = model2_data(data)
    x_train2, x_test2, y_train2, y_test2 = model2_split_data(x2, y2)
    model2_train(x_train2, x_test2, y_train2, y_test2, epochs)


def model2_check(data):
    x2, y2 = model2_data(data)
    x_train2, x_test2, y_train2, y_test2 = model2_split_data(x2, y2)
    global model_2
    model2_load_structure()
    model2_load_weights()
    model2_accuracy(x_test2, y_test2)
    print(y_test2)


def model2_data(data):
    # Filter the dataset to include only rows where "issues" equals 1
    x2 = data[data['issues'] == 1].copy()

    # Make a y data based on X
    temp_y2 = x2['trouble_codes'].values
    temp_y2_df = pd.DataFrame(temp_y2, columns=['trouble_codes'])
    mlb = MultiLabelBinarizer()
    y2 = pd.DataFrame(mlb.fit_transform(temp_y2_df['trouble_codes'].str.split(',')), columns=mlb.classes_)

    # Drop the columns that we don't need
    x2.drop(['issues', 'trouble_codes', 'time', 'vehicle_id', 'id', 'ip'], axis=1, inplace=True)

    return x2, y2


def model2_split_data(x2, y2):
    # Split the data into training and testing sets
    x_train2, x_test2, y_train2, y_test2 = train_test_split(x2, y2, test_size=0.2, random_state=42)

    return x_train2, x_test2, y_train2, y_test2


def model2_structure(input_size, output_size):
    # Define the model
    global model_2
    model_2 = tf.keras.Sequential([
        tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(128, return_sequences=True),
                                      input_shape=(input_size, 1)),
        tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(64)),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(output_size, activation='softmax')
    ])

    # Compile the model
    model_2.compile(loss='categorical_crossentropy', optimizer=tf.keras.optimizers.Adam(learning_rate=1e-4)
, metrics=['accuracy'])

    # Print the model summary
    model_2.summary()

    # Save model structure
    model2_save_structure()


def model2_accuracy(x, y):
    # Evaluate the performance of the model
    loss2, accuracy2 = model_2.evaluate(np.expand_dims(x, axis=2), y)
    print('Accuracy:', accuracy2)


def model2_predict(x_pred):
    y_pred = model_2.predict(x_pred)
    y_pred = np.round(y_pred).astype(int)
    print(y_pred)

    return y_pred


def model2_train(x_train2, x_test2, y_train2, y_test2, epochs):
    # Get the number of categories
    num_categories = y_train2.shape[1]

    # Build model structure
    model2_structure(x_train2.shape[1], num_categories)

    # Train the model
    history2 = model_2.fit(x_train2, y_train2, epochs=epochs, batch_size=32, verbose=1, validation_data=(x_test2, y_test2))

    # Save model weights
    model2_save_weights()

    # Check model accuracy
    model2_accuracy(x_test2, y_test2)

    model_plot(history2)

    y_pred = model_2.predict(x_test2)
    print(y_pred)


def model2_save_structure():
    model_save_structure('model2_structure.h5', model_2, 2)


def model2_save_weights():
    model_save_weights('model2_weights.hdf5', model_2, 2)


def model2_load_structure():
    global model_2
    model_2 = model_load_structure_backup(2)


def model2_load_weights():
    global model_2
    model_2 = model_load_weights_backup(model_2, 2)
