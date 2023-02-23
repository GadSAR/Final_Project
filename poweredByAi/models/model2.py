# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Model 2 : Predicts which issue**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


# For organizing the data
import numpy as np
from matplotlib import pyplot as plt

# For model 2
import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder

from models.methods import model_save_structure, model_load_structure, model_load_weights, model_save_weights

global model_2


def model2(data):
    x2, y2 = model2_data(data)
    x_train2, x_test2, y_train2, y_test2 = model2_split_data(x2, y2)
    model2_train(x_train2, x_test2, y_train2, y_test2)


def model2_data(data):
    # Filter the dataset to include only rows where "issues" equals 1
    x2 = data[data['issues'] == 1].copy()

    # Make a y data based on X
    temp_y2 = x2['trouble_codes'].values

    # One-hot encode the labels
    one_hot_encoder = OneHotEncoder(sparse=False)
    temp_y2 = one_hot_encoder.fit_transform(temp_y2.reshape(-1, 1))
    y2 = np.argmax(temp_y2, axis=1)  # Convert one-hot encoded labels to integer labels

    # Drop the columns that we don't need
    x2.drop(['issues', 'trouble_codes', 'time', 'vehicle_id', 'id', 'ip'], axis=1, inplace=True)

    # Print
    print(x2)
    print(y2)

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
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Bidirectional(tf.keras.layers.LSTM(64)),
        tf.keras.layers.Dropout(0.2),
        tf.keras.layers.Dense(output_size, activation='softmax')
    ])

    # Compile the model
    model_2.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

    # Print the model summary
    model_2.summary()

    # Save model structure
    model_2.save('../generated/model2_structure.h5')


def model2_train(x_train2, x_test2, y_train2, y_test2):
    # Get the number of categories
    num_categories = len(np.unique(y_train2))

    # Build model structure
    model2_structure(x_train2.shape[1], num_categories)

    # Train the model
    history2 = model_2.fit(np.expand_dims(x_train2, axis=2), y_train2, epochs=100, batch_size=32, verbose=1)

    # Save model weights
    model_2.save_weights('../generated/model2_weights.hdf5')

    # Evaluate the performance of the model
    loss2, accuracy2 = model_2.evaluate(np.expand_dims(x_test2, axis=2), y_test2)
    print('Accuracy:', accuracy2)

    # Plot the training and validation loss and accuracy
    plt.plot(history2.history['loss'], label='train_loss')
    plt.plot(history2.history['accuracy'], label='train_acc')
    plt.legend()
    plt.show()

    y_pred = model_2.predict(x_test2)
    print(y_pred)


def model2_save_structure():
    model_save_structure('model1_structure.h5', model_2, 2)


def model2_save_weights():
    model_save_weights('model1_structure.hdf5', model_2, 2)


def model2_load_structure():
    global model_2
    model_2 = model_load_structure(model_2, 2)


def model2_load_weights():
    global model_2
    model_2 = model_load_weights(model_2, 2)
