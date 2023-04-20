# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 1 : Predicts if there is an issue or not**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import numpy as np
from matplotlib import pyplot as plt

# For model 1
import tensorflow as tf
from sklearn.model_selection import train_test_split

from models.methods import model_save_structure, model_load_structure, model_load_weights, model_save_weights, \
    model_plot, model_load_weights_backup, model_load_structure_backup

global model_1


def model1(data, epochs):
    x1, y1 = model1_data(data)
    x_train1, x_test1, y_train1, y_test1 = model1_split_data(x1, y1)
    model1_train(x_train1, x_test1, y_train1, y_test1, epochs)


def model1_check(data):
    x1, y1 = model1_data(data)
    x_train1, x_test1, y_train1, y_test1 = model1_split_data(x1, y1)
    global model_1
    model1_load_structure()
    model1_load_weights()
    model1_accuracy(x_test1, y_test1)
    print(y_test1)


def model1_data(data):
    # Organize the data into x1, y1
    x1 = data.drop(['issues', 'trouble_codes', 'time', 'vehicle_id', 'id', 'ip'], axis=1).values
    y1 = data['issues'].values

    return x1, y1


def model1_split_data(x1, y1):
    # Split the data into training and testing sets
    X_train1, X_test1, y_train1, y_test1 = train_test_split(x1, y1, test_size=0.2, random_state=42)

    return X_train1, X_test1, y_train1, y_test1


def model1_structure(input_size):
    # Build a neural network model
    global model_1
    model_1 = tf.keras.Sequential([
        tf.keras.layers.Dense(64, input_shape=(input_size,), activation='relu'),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ])

    # Compile the model
    model_1.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])

    # Print the model summary
    model_1.summary()

    # Save the model structure
    model1_save_structure()


def model1_accuracy(x, y):
    # Evaluate the performance of the model
    loss1, accuracy1 = model_1.evaluate(x, y)
    print('Accuracy:', accuracy1)


def model1_train(x_train1, x_test1, y_train1, y_test1, epochs):
    # Build model structure
    model1_structure(x_train1.shape[1])

    # Train the model
    history1 = model_1.fit(x_train1, y_train1, epochs=epochs, batch_size=32, verbose=1,
                           validation_data=(x_test1, y_test1))

    # Save weights
    model1_save_weights()

    model1_accuracy(x_test1, y_test1)

    model_plot(history1)

    model1_predict(x_test1)


def model1_predict(x_pred):
    y_pred = model_1.predict(x_pred)
    y_pred = np.round(y_pred).astype(int)
    print(y_pred)

    return y_pred


def model1_save_structure():
    model_save_structure('model1_structure.h5', model_1, 1)


def model1_save_weights():
    model_save_weights('model1_weights.hdf5', model_1, 1)


def model1_load_structure():
    global model_1
    model_1 = model_load_structure_backup(1)


def model1_load_weights():
    global model_1
    model_1 = model_load_weights_backup(model_1, 1)
