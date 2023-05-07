# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


from src.python.services.data_service import DataService
from src.python.models.model1 import Model1


def main():
    # Load the dataset
    data = DataService.get_data()

    # Start model
    Model1.model1_check(data)


if __name__ == '__main__':
    main()

