# -*- coding: utf-8 -*-
"""s18579_gr12c_PRO4.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/11N_To6SCQyiEiZ6zu7Hw21KzaWJQ_i5U

1.Wczytaj dane z pliku daneXX.txt. Zaproponuj i zrealizuj podział tych danych na dane treningowe i dane testowe,

2.Zaproponuj liniowy model parametryczny Model 1. Określ parametry modelu stosując metodę najmniejszych kwadratów dla danych treningowych,

3.Zweryfikuj poprawność Modelu 1,

4.Zaproponuj bardziej złożony model parametryczny Model 2. Określ parametry modelu stosując metodę najmniejszych kwadratów dla danych treningowych,

5.Zweryfikuj poprawność Modelu 2,

6.Porównaj oba modele.
"""

from google.colab import drive
drive.mount('/content/drive', force_remount=True)

!ln -s /content/drive/MyDrive/MIW/dan_pro/ dane

def Exy(x, y):
  i = 0
  sum = 0
  while i < len(x):
    sum += x[i]*y[i]
    i+=1
  return sum

def Ex2y(x, y):
  i = 0
  sum = 0
  while i < len(x):
    sum += (x[i]**2)*y[i]
    i+=1
  return sum

def Ex(x):
  i = 0
  sum = 0
  while i < len(x):
    sum += x[i]
    i+=1
  return sum

def Ey(y):
  i = 0
  sum = 0
  while i < len(y):
    sum += y[i]
    i+=1
  return sum

def Ex2(x):
  i = 0
  sum = 0
  while i < len(x):
    sum += x[i]**2
    i+=1
  return sum

def Ex3(x):
  i = 0
  sum = 0
  while i < len(x):
    sum += x[i]**3
    i+=1
  return sum

def Ex4(x):
  i = 0
  sum = 0
  while i < len(x):
    sum += x[i]**4
    i+=1
  return sum

def w(x, y):
  return Exy(x,y)/Ex2(x)

def w1(x, y):
  return ( (len(x) * Exy(x,y))  -  (Ex(x) * Ey(y)) )/( (len(x) * Ex2(x) ) - (Ex(x)**2) )

def w0(x,y):
  return ( (Ey(y) * Ex2(x)) - (Ex(x) * Exy(x,y)) ) / ( (len(x) * Ex2(x) ) - (Ex(x)**2) )

def regLinear(w, x):
  i = 0
  y = []
  while i < len(x):
    y.append(w*x[i])
    i+=1
  return y

def regLinearBetter(w0,w1, x):
  i = 0
  y = []
  while i < len(x):
    y.append((w1*x[i])+w0)
    i+=1
  return y

def regVector(w2,w1,w0, x):
  i = 0
  y = []
  while i < len(x):
    y.append((w2*(x[i]**2))+(w1*x[i])+w0)
    i+=1
  return y

def checkDiff0(x, y, w):
  i = 0
  sum = 0
  while i < len(x):
    sum += ((y[i] - (w*x[i]))**2)
    i+=1
  return sum

def checkDiff1(x, y, w0, w1):
  i = 0
  sum = 0
  while i < len(x):
    sum += ((y[i] - (w1*x[i]+w0))**2)
    i+=1
  return sum

def checkDiff2(x, y, w0, w1, w2):
  i = 0
  sum = 0
  while i < len(x):
    sum += ((y[i] - ((w0*(x[i]**2))+(w1*x[i])+w2))**2)
    i+=1
  return sum

import matplotlib.pyplot as plt
import numpy as np
from sklearn.model_selection import train_test_split

i=1
file = open("dane/dane{}.txt".format(i), "r")
X = []
y = []
for f in file:
  d = f.split(" ")
  d.remove('\n')
  X.append(float(d[0]))
  y.append(float(d[1]))
print(X)
print(y)
#Podzielenie na dane treningowe i dane testowe
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3,random_state=1)

#
reg1 = regLinear(w(X_train,y_train),X)
reg2 = regLinearBetter(w0(X_train,y_train),w1(X_train,y_train),X)

#
matrix = np.array([
                   [Ex4(X_train), Ex3(X_train), Ex2(X_train)],
                   [Ex3(X_train), Ex2(X_train), Ex(X_train)],
                   [Ex2(X_train), Ex(X_train), len(X_train)]
                   ])
multi = np.array([Ex2y(X_train,y_train),Exy(X_train,y_train),Ey(y_train)])
vector = np.matmul(np.linalg.inv(matrix), multi)

reg3 = regVector(vector[0],vector[1],vector[2],X)
print(reg3)
print(vector)
print("Poprawność Model0:\t"+str(checkDiff0(X,y,w(X_train,y_train))))
print("Poprawność Model1:\t"+str(checkDiff1(X,y,w0(X_train,y_train),w1(X_train,y_train))))
print("Poprawność Model2:\t"+str(checkDiff2(X,y,vector[0],vector[1],vector[2])))
plt.plot(X_test, y_test, 'r*')
plt.plot(X_train, y_train, 'b*')
plt.plot(X,reg1)
plt.show()
plt.plot(X_test, y_test, 'r*')
plt.plot(X_train, y_train, 'b*')
plt.plot(X,reg2)
plt.show()
plt.plot(X_test, y_test, 'r*')
plt.plot(X_train, y_train, 'b*')
plt.plot(X,reg3)
plt.show()