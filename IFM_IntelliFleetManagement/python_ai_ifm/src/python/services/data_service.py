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
