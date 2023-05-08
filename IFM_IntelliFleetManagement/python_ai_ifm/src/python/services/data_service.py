import pandas as pd
import mysql.connector


class DataService:
    def __init__(self):
        self.mysql = mysql
        self.mydb = self.connect_to_database()

    def connect_to_database(self):
        mydb = self.mysql.connector.connect(
            host="localhost",
            user="root",
            password="admin",
            database="ifm_database"
        )

        my_cursor = mydb.cursor()

        # Create the first table if it does not exist
        my_cursor.execute(
            "CREATE TABLE IF NOT EXISTS table1 (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT)")

        # Create the second table if it does not exist
        my_cursor.execute(
            "CREATE TABLE IF NOT EXISTS table2 (id INT AUTO_INCREMENT PRIMARY KEY, address VARCHAR(255), phone VARCHAR(20))")

        return mydb

    def get_data(self):
        my_cursor = self.mydb.cursor()
        my_cursor.execute("SELECT * FROM obd2scannerdata")
        my_result = my_cursor.fetchall()
        df = pd.DataFrame(my_result, columns=my_cursor.column_names)
        return df

    def get_last_car_data(self, carId):
        my_cursor = self.mydb.cursor()
        sql = "SELECT * FROM obd2scannerdata WHERE VEHICLE_ID = %s ORDER BY TIME LIMIT 1"
        my_cursor.execute(sql, (carId,))
        my_result = my_cursor.fetchall()
        df = pd.DataFrame(my_result, columns=my_cursor.column_names)
        df.drop(['issues', 'trouble_codes', 'time', 'vehicle_id', 'id', 'ip', 'driver_id'], axis=1, inplace=True)
        return df
