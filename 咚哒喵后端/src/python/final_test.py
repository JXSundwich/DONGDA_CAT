# -*- coding=utf-8 -*-
import cv2
import os
#from skimage import io,transform
import sys
from sys import argv
import numpy as np
#import paramiko
import urllib.request

 
 #计算方差
def getss(list):
    #计算平均值
    avg = sum(list)/len(list)
    #定义方差变量ss，初值为0
    ss=0
    #计算方差
    for l in list:
        ss+=(l-avg)*(l-avg)/len(list)
    #返回方差
    return ss
 
#获取每行像素平均值
def getdiff(img):
    #定义边长
    Sidelength=30
    #缩放图像
    img=cv2.resize(img,(Sidelength,Sidelength),interpolation=cv2.INTER_CUBIC)
    #灰度处理
    gray=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    #avglist列表保存每行像素平均值
    avglist=[]
    #计算每行均值，保存到avglist列表
    for i in range(Sidelength):
        avg=sum(gray[i])/len(gray[i])
        avglist.append(avg)
    #返回avglist平均值
    return avglist

# 连接数据库
# # 实例化一个transport对象
# transport = paramiko.Transport(('http://kawamurareo.work', 22))
# # 建立连接
# transport.connect(username='Administrator', password='Kawamura_Reo322')
# sftp = paramiko.SFTPClient.from_transport(transport)
# data = sftp.listdir('babel_ncc/upload/image')
# print (data)
# for imagename in data:
#     imagepath = os.path.join('http://127.0.0.1:9999/image/', imagename)
#     print (imagepath)
#     resp = urllib.urlopen(imagepath)
#     image = np.asarray(bytearray(resp.read()), dtype="uint8")
#     image = cv2.imdecode(image,cv2.IMREAD_COLOR)
#     cv2.imshow('image', image)
#     cv2.waitKey(0)
#     print (image)


# 加载猫脸检测器


catPath = "haarcascade_frontalcatface.xml"
faceCascade = cv2.CascadeClassifier(catPath)
print('-'*20)


# 初始化log文件
sys.stdout = open('resualt.log', mode = 'w',encoding='utf-8')
x = 0
print(x)

# 读取图片并灰度化


gpus = sys.argv[0]
#gpus = [int(gpus.split(','))]
filePath1 = sys.argv[1]
filePath2 = sys.argv[2]

#print(gpus)
print(filePath1)
print(filePath2)

url1 = filePath1
resp = urllib.request.urlopen(url1)
img = np.asarray(bytearray(resp.read()), dtype="uint8")
img = cv2.imdecode(img, cv2.IMREAD_COLOR)
# cv2.imshow('URL2Image',img)
# cv2.waitKey()

url2 = filePath2
resp2 = urllib.request.urlopen(url2)
img2 = np.asarray(bytearray(resp2.read()), dtype="uint8")
img2 = cv2.imdecode(img2, cv2.IMREAD_COLOR)
# cv2.imshow('URL2Image',img2)
# cv2.waitKey()



#filepath="C:/Users/yanyixiao/Desktop/mycat/cat-photo/09.jpg"
#print('@'*20)
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
gray2 = cv2.cvtColor(img2, cv2.COLOR_BGR2GRAY)
# 猫脸检测
#print('#'*20)
faces = faceCascade.detectMultiScale(
    gray,
    scaleFactor= 1.02,
    minNeighbors=3,
    minSize=(150, 150),
    flags=cv2.CASCADE_SCALE_IMAGE
)
faces2 = faceCascade.detectMultiScale(
    gray2,
    scaleFactor= 1.02,
    minNeighbors=3,
    minSize=(150, 150),
    flags=cv2.CASCADE_SCALE_IMAGE
)
# 框出猫脸并加上文字说明
#print('#'*20)
for (x, y, w, h) in faces:
    #cv2.rectangle(img, (x, y), (x+w, y+h), (0, 0, 255), 2)
    #cv2.putText(img,'Cat',(x,y-7), 3, 1.2, (0, 255, 0), 2, cv2.LINE_AA)
    cropImg1 = img[y:y+h,x:x+w]

for (x, y, w, h) in faces2:
    #cv2.rectangle(img, (x, y), (x+w, y+h), (0, 0, 255), 2)
    #cv2.putText(img,'Cat',(x,y-7), 3, 1.2, (0, 255, 0), 2, cv2.LINE_AA)
    cropImg2 = img2[y:y+h,x:x+w]
    
 #显示图片并保存
#print('#'*20)
#cv2.imshow('Cat?', cropImg1)
#print('#'*20)
#cv2.imwrite("cat33.jpg",cropImg1)
#print('#'*20)
#c = cv2.waitKey(0)

###到这里，cropImg1是接下来的猫头
cat1_dif = getdiff(cropImg1)
cat1_fac = getss(cat1_dif)
print("原图方差是：")
print(cat1_fac)

cat2_dif = getdiff(cropImg2)
cat2_fac = getss(cat2_dif)
print("比较图方差是：")
print(cat2_fac)

sm = 1 - abs(cat1_fac-cat2_fac)/(cat1_fac+cat2_fac)

print("相似度是：")
print(sm)


if(sm>0.85):
    sys.stdout = open('resualt.log', mode = 'w',encoding='utf-8')
    x = 1
    print(x)
else:
    sys.stdout = open('resualt.log', mode = 'w',encoding='utf-8')
    x = 0
    print(x)
   
    

    


