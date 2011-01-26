#include <cv.h>
#include <highgui.h>
#include <stdio.h>

IplImage *process(IplImage **_img);
void cvOpen(const CvArr *src, CvArr *dst, IplConvKernel *element);
void cvClose(const CvArr *src, CvArr *dst, IplConvKernel *element);

int main(int argc, char **argv)
{
	// Load in image given and set up two windows to display original and processed image
	cvInitSystem(argc,argv);
	CvCapture* cam = cvCreateCameraCapture(0);
    
	while(1)
	{

		IplImage* input = cvQueryFrame(cam);
		
		if (input)
		{
			cvNamedWindow("Original:", CV_WINDOW_AUTOSIZE);
			cvNamedWindow("Processed:", CV_WINDOW_AUTOSIZE);
			cvMoveWindow("Original:", 200, 200);
			cvMoveWindow("Processed:", 200, 200);

			// Process the image
			IplImage* orig = cvCloneImage(input);
			IplImage* processedImage = process(&input);

			// Actually show both windows with corresponding images
			cvShowImage("Original:", orig);
		
			if (!processedImage)
			{
				cvShowImage("Processed:",orig);
			} else {
				cvShowImage("Processed:",processedImage);
			}					
		}		
		if (cvWaitKey(30) >= 0)
				return 0;	

	}
}
                      
IplImage *process(IplImage **_img)
{
	// Convert to HSV. Better for locating colours
	IplImage *img = *_img;                                               
	CvSize size = cvGetSize(img);
	IplImage *hsv = cvCreateImage(size, IPL_DEPTH_8U,3);                        
	cvCvtColor(img,hsv,CV_BGR2HSV);

	// Look for the red colour and create a mask of just red objects
	CvMat *mask = cvCreateMat(size.height,size.width,CV_8UC1);
	cvInRangeS(hsv,cvScalar(0.00*256,0.50*256,0.50*256,0),
				   cvScalar(0.05*256,1.0*256,1.0*256,0), mask);
	cvReleaseImage(&hsv);

	// Morphological operations to reduce noise.
	cvSmooth(mask,mask,CV_GAUSSIAN,5,5,0,0); // Added extra noise reduction with gaussian blur
	IplConvKernel *se21 = cvCreateStructuringElementEx(1,1,0,0,CV_SHAPE_ELLIPSE,NULL);
	IplConvKernel *se11 = cvCreateStructuringElementEx(1,1,0,0,CV_SHAPE_ELLIPSE,NULL);
	cvClose(mask,mask,se21);
	cvOpen(mask,mask,se11);
	cvReleaseStructuringElement(&se21);
	cvReleaseStructuringElement(&se11);

	IplImage *contourImage = cvCreateImage(size,8,1);                                 
	cvCopy(mask,contourImage,NULL);
	CvMemStorage *storage = cvCreateMemStorage(0);
	// Not sure if you need two smoothing calls but try anyway
    cvSmooth(contourImage, contourImage, CV_GAUSSIAN, 3, 3, 0, 0);

    // Create list of contours
    CvSeq* contour = NULL;
    CvSeq* contourLow = NULL;
              
	// Actually find contours and store in the list
    cvFindContours(contourImage, storage, &contour, sizeof(CvContour), CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cvPoint(0,0));
    
    if (!contour)
		return NULL;
		
    contourLow = cvApproxPoly(contour,sizeof(CvContour), storage, CV_POLY_APPROX_DP,1,1);
	
	// This bit needs to change. Doesn't check if it's a circle. Will use compactness as a measure.
	// Therefore A ~= P^2/4pi. A = cvContourArea P = cvArcLength
	
	double max = 0;
	CvSeq* largestArea = NULL;

    for ( ; contourLow != 0; contourLow = contourLow->h_next)
	{
		double area = cvContourArea(contourLow,CV_WHOLE_SEQ,0);
		
		if (area > max)
		{
			max = area;
			largestArea = contourLow;
		}    
	}
	
	CvRect rect;
	CvPoint pt1,pt2,center;
	rect = cvBoundingRect(largestArea, 0);
	pt1.x = rect.x;
	pt2.x = (rect.x+rect.width);
	pt1.y = rect.y;
	pt2.y = (rect.y+rect.height);

	center.x = pt1.x + (rect.width/2);
	center.y = pt1.y + (rect.height/2);

	cvCircle(img,center,1,CV_RGB(0,0,0),CV_FILLED,8,0);
	cvRectangle(img,pt1,pt2, CV_RGB(0,0,255), 1, 8, 0);

	cvReleaseImage(&contourImage);
	cvReleaseMemStorage(&storage);

	return img;

}

// Functions to reduce noise in the masked image
void cvOpen(const CvArr *src, CvArr *dst, IplConvKernel *element)
{
	cvErode(src,dst,element,1);
	cvDilate(src,dst,element,1);
}

void cvClose(const CvArr *src, CvArr *dst, IplConvKernel *element)
{                                                            
	cvDilate(src,dst,element,1);
	cvErode(src,dst,element,1);
}
