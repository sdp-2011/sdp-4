import cv
import math

mods = [0.0, 0.4, 0.4, 0.05, 1.0, 1.0, 0.3, 0.3, 0.5, 0.8, 1.0, 1.0, 0.1, 0.36, 0.37, 0.2, 1.0, 1.0]

def onHLowerRChange(position):  mods[0] = position/255.0
def onSLowerRChange(position):  mods[1] = position/255.0
def onVLowerRChange(position):  mods[2] = position/255.0
def onHUpperRChange(position):  mods[3] = position/255.0
def onSUpperRChange(position):  mods[4] = position/255.0
def onVUpperRChange(position):  mods[5] = position/255.0

def onHLowerBChange(position):  mods[6] = position/255.0
def onSLowerBChange(position):  mods[7] = position/255.0
def onVLowerBChange(position):  mods[8] = position/255.0
def onHUpperBChange(position):  mods[9] = position/255.0
def onSUpperBChange(position):  mods[10] = position/255.0
def onVUpperBChange(position):  mods[11] = position/255.0

def onHLowerYChange(position):  mods[12] = position/255.0
def onSLowerYChange(position):  mods[13] = position/255.0
def onVLowerYChange(position):  mods[14] = position/255.0
def onHUpperYChange(position):  mods[15] = position/255.0
def onSUpperYChange(position):  mods[16] = position/255.0
def onVUpperYChange(position):  mods[17] = position/255.0

def find_object_descriptors(mask):
    moments = cv.Moments(mask, 1)
    M00 = cv.GetSpatialMoment(moments,0,0)
    M10 = cv.GetSpatialMoment(moments,1,0)
    M01 = cv.GetSpatialMoment(moments,0,1)
    M20 = cv.GetSpatialMoment(moments,2,0)
    M02 = cv.GetSpatialMoment(moments,0,2)
    M11 = cv.GetSpatialMoment(moments,1,1)
    
    # Protect against division by 0    
    if M00 == 0:
        M00 = 0.01
    
    x_bar = (M10/M00)
    y_bar = (M01/M00)

    mu20 = (M20/M00) - x_bar**2
    mu02 = (M02/M00) - y_bar**2
    mu11 = (M11/M00) - x_bar * y_bar
    error = math.sqrt(4 * (mu11**2) + (mu20 - mu02)**2)

    orientation = math.degrees(math.atan2(2*mu11, mu20 - mu02 + error))
    center_point = (int(x_bar), int(y_bar))

    return [center_point, orientation] 

def find_object(img, colour):
    '''
    Finds the objects in an image with given colour.

    Arguments:
    img          -- the image to be processed
    colour	 -- the colour to look for (red, blue or yellow)

    Returns:
    Point representing object's centre of mass

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
            redLower = cv.Scalar(mods[0]*256, mods[1]*256, mods[2]*256)
            redUpper = cv.Scalar(mods[3]*256, mods[4]*256, mods[5]*256)
            cv.InRangeS(hsv, redLower, redUpper, mask)              
            cv.ShowImage("Red:",mask)
    elif (colour == "BLUE"):
            blueLower = cv.Scalar(mods[6]*256, mods[7]*256, mods[8]*256)
            blueUpper = cv.Scalar(mods[9]*256, mods[10]*256, mods[11]*256)
            cv.InRangeS(hsv, blueLower, blueUpper, mask)
            cv.ShowImage("Blue:",mask)
    elif (colour == "YELLOW"):
            yellowLower = cv.Scalar(mods[12]*256, mods[13]*256, mods[14]*256)
            yellowUpper = cv.Scalar(mods[15]*256, mods[16]*256, mods[17]*256)
            cv.InRangeS(hsv, yellowLower, yellowUpper, mask)
            cv.ShowImage("Yellow:",mask)
            
    # Count white pixels to make sure program doesn't crash if it finds nothing
    if (cv.CountNonZero(mask) < 3):
            return [(0, 0),0]

    # Clean up the image to reduce anymore noise in the binary image
    cv.Smooth(mask, mask, cv.CV_GAUSSIAN, 9, 9, 0, 0)
    convKernel = cv.CreateStructuringElementEx(1, 1, 0, 0, cv.CV_SHAPE_ELLIPSE)
    cv.Erode(mask, mask, convKernel, 1)
    cv.Dilate(mask, mask, convKernel, 1)

    return find_object_descriptors(mask)
