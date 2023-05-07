# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


from src.python.services.data_service import DataService
from src.python.models.model2 import Model2


def main():
    # Load the dataset
    data = DataService.get_data()

    # Start model
    epochs = 10
    Model2.model2_build(data, epochs)


if __name__ == '__main__':
    main()

