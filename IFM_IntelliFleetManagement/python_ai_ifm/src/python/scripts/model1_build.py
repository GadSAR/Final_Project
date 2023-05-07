# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#

from src.python.services.data_service import DataService
from src.python.models.model1 import Model1


def main():
    # Load the dataset
    data = DataService.get_data()

    # Start model
    epochs = 10
    Model1.model1(data, epochs)


if __name__ == '__main__':
    main()
