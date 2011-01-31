import cv


def findObject(img, colour):
    '''
    Finds the objects in an image with given colour.

    Arguments:
    img	    -- the image to be processed
    colour  -- the colour to look for (red, blue or yellow)

    Returns:
    Attributes of bounding rectangle around an object

    '''

    # Convert to hsv
    size = cv.GetSize(img)
    tempImage = cv.CreateImage((size[0] / 2, size[1] / 2), 8, 3)

    # Reduce noise by down- and up-scaling input image
    cv.PyrDown(img, tempImage, 7)
    cv.PyrUp(tempImage, img, 7)
    hsv = cv.CreateImage(size, cv.IPL_DEPTH_8U, 3)
    cv.CvtColor(img, hsv, cv.CV_BGR2HSV)

    # Convert to binary image based on colour
    mask = cv.CreateMat(size[1], size[0], cv.CV_8UC1)
    maskSize = cv.GetSize(mask)
    if (colour == "RED"):
		redLower = cv.Scalar(0.00 * 256, 0.50 * 256, 0.50 * 256)
		redUpper = cv.Scalar(0.05 * 256, 1.00 * 256, 1.00 * 256)
		cv.InRangeS(hsv, redLower, redUpper, mask)
		cv.NamedWindow("Red:",cv.CV_WINDOW_AUTOSIZE)
		cv.ShowImage("Red:",mask)

    elif (colour == "BLUE"):
    	blueLower = cv.Scalar(0.35 * 256, 0.30 * 256, 0.30 * 256)
		blueUpper = cv.Scalar(0.80 * 256, 1.00 * 256, 1.00 * 256)
		cv.InRangeS(hsv, blueLower, blueUpper, mask)
		cv.NamedWindow("Blue:",cv.CV_WINDOW_AUTOSIZE)
		cv.ShowImage("Blue:",mask)

    elif (colour == "YELLOW"):
		yellowLower = cv.Scalar(0.10 * 256, 0.45 * 256, 0.50 * 256)
		yellowUpper = cv.Scalar(0.20 * 256, 1.00 * 256, 1.00 * 256)
		cv.InRangeS(hsv, yellowLower, yellowUpper, mask)
		cv.NamedWindow("Yellow:",cv.CV_WINDOW_AUTOSIZE)
		cv.ShowImage("Yellow:",mask)

    # Count white pixels to make sure program doesn't crash if it finds nothing
    if (cv.CountNonZero(mask) < 3):
	    return (0, 0, 0, 0)  # (0, 0, 0, 0) is a rectangle with no attributes

    # Clean up the image to reduce anymore noise in the binary image
    cv.Smooth(mask, mask, cv.CV_GAUSSIAN, 9, 9, 0, 0)
    convKernel = cv.CreateStructuringElementEx(1, 1, 0, 0, cv.CV_SHAPE_ELLIPSE)
    cv.Erode(mask, mask, convKernel, 1)
    cv.Dilate(mask, mask, convKernel, 1)
    cv.Dilate(mask, mask, convKernel, 1)
    cv.Erode(mask, mask, convKernel, 1)

    contourImage = cv.CreateImage(size, 8, 1)
    cv.Copy(mask, contourImage, None)

    storage = cv.CreateMemStorage()
    contours = cv.FindContours(contourImage, storage, cv.CV_RETR_EXTERNAL, cv.CV_CHAIN_APPROX_SIMPLE)

    if len(contours) > 1:
	    contourLow = cv.ApproxPoly(contours, storage, cv.CV_POLY_APPROX_DP)
	    rect = cv.BoundingRect(contourLow, 0)
	    return rect

    return (0, 0, 0, 0)



'''
Continually grabs new frame from camera and looks for each object.

'''

cv.NamedWindow("Original:", cv.CV_WINDOW_AUTOSIZE)
cv.NamedWindow("Processed:", cv.CV_WINDOW_AUTOSIZE)
cam = cv.CaptureFromCAM(0)

while (True):
	image = cv.QueryFrame(cam)
	cropRect = (75, 80, 554, 340)
	roi = cv.SetImageROI(image, cropRect)
	orig = cv.CloneImage(image)
	processed = cv.CloneImage(orig)

	ballRect = findObject(image, "RED")
	blueRect = findObject(image, "BLUE")
	yellowRect = findObject(image, "YELLOW")

	cv.Rectangle(processed, (ballRect[0], ballRect[1]), 
			(ballRect[0] + ballRect[2], ballRect[1] + ballRect[3]), 
			cv.RGB(255, 0, 0), 1, 8, 0)
	cv.Rectangle(processed, (blueRect[0], blueRect[1]), 
			(blueRect[0] + blueRect[2], blueRect[1] + blueRect[3]), 
			cv.RGB(0, 0, 255), 1, 8, 0)
	cv.Rectangle(processed, (yellowRect[0], yellowRect[1]), 
			(yellowRect[0] + yellowRect[2], yellowRect[1] + yellowRect[3]), 
			cv.RGB(255, 255, 0), 1, 8, 0)

	cv.ShowImage("Original:", orig)
	cv.ShowImage("Processed:", processed)

	cv.WaitKey(30)
