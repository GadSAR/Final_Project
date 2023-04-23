# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


from models.methods import get_data
from models.model2 import model2


def main():
    # Load the dataset
    data = get_data()

    # Start model
    epochs = 10
    model2(data, epochs)


if __name__ == '__main__':
    main()

