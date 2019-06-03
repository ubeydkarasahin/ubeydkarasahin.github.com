import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn import svm
from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn.metrics import precision_score
from sklearn.metrics import recall_score
from sklearn.metrics import roc_curve, auc
import matplotlib.pyplot as plt
import random

data=pd.read_csv("IMDB-Movie-Data.csv") #use pandas to read csv file, store data in dataframe called "data"
data=data.dropna(axis=0, how='any') #"axis" -Drop rows which contain missing values, "how" - If any NA values are present, drop that row or column

#Data for Evaluation
X = data[data.columns[6:32]] #set up data as data frame (pandas)
Y=data.iloc[:,-1] # select all data minus the success column

#Train and Test Splitting
X_train,X_test,Y_train,Y_test=train_test_split(X,Y,test_size=0.25,random_state=0) #split matrices into random train/test sets
scaler = StandardScaler() #declare scaler which removes mean and variance, standardizes on z-scores
X_train =scaler.fit_transform(X_train) #fit to data, then transform it using scaler
X_test=scaler.transform(X_test) #transform test data - standardization via data center and scale

clf = svm.SVC() #call support vector classification method in sklearn.svm, pass classifier - set up and train model
y_pred=clf.fit(X_train,Y_train).predict(X_test) #test model

#Analyze results
conf_mat = confusion_matrix(Y_test,y_pred) #compare prediction results versus actual data. Y_test is our true values, y_pred is our estimated values
acc = accuracy_score(Y_test,y_pred) #determine accurace classification score. normalized by default. Y_test is our true values, y_pred is our estimated values
precision = precision_score(Y_test,y_pred) #determine precision. Y_test is our true values, y_pred is our estimated values
recall = recall_score(Y_test,y_pred) #calculate the classifier's ability to find all positive samples. Y_test is our true values, Y_pred is our estimated values


#Print Results
print('Confusion Matrix is :')
print(conf_mat)
print('\nAccuracy is :', acc)
print('\nPrecision is :', precision)
print('\nRecall is: ', recall)