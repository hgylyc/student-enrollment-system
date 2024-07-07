import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestRegressor
from datetime import datetime
import json

def predict():
    # 硬编码CSV文件路径
    file_path ='C:/Users/monster/Desktop/pro/student-enrollment-system-new/code/student-enrollment-sysytem/python/data.csv'

    #file_path ='data.csv'

    # 读取CSV文件
    df = pd.read_csv(file_path, parse_dates=['date'])

    # 添加年份和日期信息
    df['year'] = df['date'].dt.year
    df['day_of_year'] = df['date'].dt.dayofyear

    # 提取过去几年的数据
    historical_data = df[df['year'] < 2024]

    # 准备训练数据
    X_train = historical_data[['year', 'day_of_year']]
    y_train = historical_data['reported_count']

    # 构建随机森林回归模型
    model = RandomForestRegressor(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)

    days_to_predict = pd.DataFrame({
        'year': [2024] * 5,
        'day_of_year': [datetime(2024, 7, i).timetuple().tm_yday for i in range(5, 10)]
    })

    # 预测今年的数据
    predicted_counts = model.predict(days_to_predict)

    # 将预测结果向上取整
    predicted_counts = np.ceil(predicted_counts).astype(int)

    # 将预测结果转换为DataFrame
    predicted_df = pd.DataFrame({
        'date': pd.date_range(start='2024-07-05', end='2024-07-09'),
        'predicted_count': predicted_counts
    })

    # 将日期转换为字符串
    predicted_df['date'] = predicted_df['date'].dt.strftime('%Y-%m-%d')

    return predicted_df.to_dict(orient='records')

if __name__ == "__main__":
    predictions = predict()
    print(json.dumps(predictions, indent=4))



# See PyCharm help at https://www.jetbrains.com/help/pycharm/
