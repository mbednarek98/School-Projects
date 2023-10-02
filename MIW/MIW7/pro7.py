# -*- coding: utf-8 -*-
"""s18579_gr12c_pro7.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1J-T6a_gJx2XMC_6nyPVaU4wWGqMB_WrQ

# Rozbuduj sieć konwolucyjną z projektu 6 do autokodera, poprzez usunięcie warstw klasyfikujących i dodanie symetrycznych warstw *Conv2DTranspose*. Przystosuj sieć do wykorzystania danych mnist (Ręcznie pisane cyfry).
"""

import numpy as np
import matplotlib.pyplot as plt
from keras.datasets import mnist

# Przystosuj sieć do wykorzystania danych mnist (Ręcznie pisane cyfry).
(x_train, _), (x_test, _) = mnist.load_data()
x_train = x_train.astype('float32') / 255
x_test = x_test.astype('float32') / 255
print(x_train.shape)
print(x_test.shape)

plt.figure(figsize=(20, 4))
for i in range(20):
    ax = plt.subplot(2, 20, i + 1)
    plt.imshow(x_test[i])
    plt.gray()
    ax.get_xaxis().set_visible(False)
    ax.get_yaxis().set_visible(False)
plt.show()

"""## Przykład autoencodera"""

from keras.layers import Input, Dense, Conv2D, UpSampling2D, MaxPooling2D, Conv2DTranspose, LeakyReLU
from keras import Model, utils

# Rozbuduj sieć konwolucyjną z projektu 6 do autokodera, poprzez usunięcie warstw klasyfikujących i dodanie symetrycznych warstw Conv2DTranspose.
inputs = Input((28,28,1))

encoded_3 = Conv2D(10,(5,5),activation="relu")(inputs)
encoded_2 = MaxPooling2D(2,2)(encoded_3)
encoded_1 = Conv2D(20,(2,2),activation="relu")(encoded_2)
encoded = MaxPooling2D((2,2))(encoded_1)

decoded_4 = UpSampling2D((2,2))(encoded)
decoded_3 = Conv2DTranspose(20,(2,2),activation="relu")(decoded_4)
decoded_2 = UpSampling2D((2,2))(decoded_3)
decoded_1 = Conv2DTranspose(10,(5,5),activation="sigmoid")(decoded_2)
decoded = Conv2DTranspose(1,(3,3),activation="sigmoid")(decoded_1)

auto_encoder = Model(inputs, decoded)
auto_encoder.compile()
auto_encoder.summary()

auto_encoder.compile(loss="binary_crossentropy",optimizer='adam',metrics=None)

# Wytrenuj model używając jedynie danych wejściowych, wyjście modelu równa się jego wejściu.
auto_encoder.fit(x_train,x_train,batch_size=21,epochs=1,validation_data=(x_test,x_test))

encoder = Model(inputs, encoded)
encoded_imgs = encoder.predict(x_test)

# Zaprezentuj na danych testowych jak wyglądają dane wejściowe, dane zwrócone przez encoder i dane wyjściowe.
for i in range(5):
  print("Image numer: "+str(i))
  print("Before")
  plt.imshow(x_test[i].reshape(28,28))
  plt.show()
  print("Encoded")
  plt.imshow(encoded_imgs[i].reshape(1,20,25,1).reshape(20,25))
  plt.show()
  print("After")
  result = auto_encoder.predict(x_test[i].reshape(1,28,28,1))
  plt.imshow(result.reshape(28,28))
  plt.show()
  print("\n\n\n")

"""# Wytrenuj model używając jedynie danych wejściowych, wyjście modelu równa się jego wejściu."""



"""# Zaprezentuj na danych testowych jak wyglądają dane wejściowe, dane zwrócone przez encoder i dane wyjściowe."""
