# -----------------------------------------------------------------------------------------------------------------------------------------------#

"""# **Main**"""

# -----------------------------------------------------------------------------------------------------------------------------------------------#


from src.python.services.data_service import DataService
from src.python.models.model2 import Model2


def main():
    # Load the dataset
    data = DataService.get_data()

    # Start model
    Model2.model2_check(data)


if __name__ == '__main__':
    main()
