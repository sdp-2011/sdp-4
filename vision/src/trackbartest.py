import cv

def on_change(position):
	print 1
	
cv.NamedWindow("Trackbar Test:", cv.CV_WINDOW_AUTOSIZE)

cv.CreateTrackbar("H","Trackbar Test:",0,1,on_change)
image = cv.LoadImage("bg.png")
cv.ShowImage("Trackbar Test:", image)

cv.WaitKey(0)
