# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


from src.python.services.data_service import DataService
from src.python.models.model3 import Model3


def main():
    # Load the dataset
    data = DataService.get_data()

    # Start model
    Model3.model3(data)


if __name__ == '__main__':
    main()

