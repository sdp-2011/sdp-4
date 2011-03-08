import cv
import os

num_boards = 0
num_horizontal = 7
num_vertical = 3

num_boards = input("Enter the number of snapshots")
num_framestep = input("Enter the number of frames to skip")

board_total = num_horizontal * num_vertical
board_size = (num_horizontal, num_vertical)

cam = cv.CaptureFromCAM(0)
os.system('/home/s0824321/sdp/sdp/vision/src/modv4l.sh')

cv.NamedWindow("Snapshot")
cv.NamedWindow("Raw Video")

image_points = cv.CreateMat(num_boards * board_total, 2, cv.CV_32FC1)
object_points = cv.CreateMat(num_boards * board_total, 3, cv.CV_32FC1)
point_counts = cv.CreateMat(num_boards, 1, cv.CV_32SC1)
intrinsic = cv.CreateMat(3, 3, cv.CV_32FC1)
distortion = cv.CreateMat(4, 1, cv.CV_32FC1)

corners = None
corner_count = 0
successes = 0
step = 0
frame = 0

image = cv.QueryFrame(cam)
gray_image = cv.CreateImage(cv.GetSize(image), 8, 1)

while (successes < num_boards):
    frame += 1
    if (frame % num_framestep == 0):
	corners = cv.FindChessboardCorners(image, board_size, cv.CV_CALIB_CB_ADAPTIVE_THRESH | cv.CV_CALIB_CB_FILTER_QUADS)
	corners = corners[1]
	cv.CvtColor(image, gray_image, cv.CV_BGR2GRAY)
	cv.FindCornerSubPix(gray_image, corners, (11, 11), (0, 0), (cv.CV_TERMCRIT_EPS+cv.CV_TERMCRIT_ITER, 30, 0.1))
	if(len(corners) > 1):
	    cv.DrawChessboardCorners(image, board_size, corners, 1)
	if(len(corners) == board_total):
	    cv.ShowImage("Snapshot", image)
	    step = successes*board_total
	    i = step
	    for j in range(board_total):
		cv.Set2D(image_points, i, 0, corners[j][0])
		cv.Set2D(image_points, i, 1, corners[j][1])
		cv.Set2D(object_points, i, 0, float(j)/num_horizontal)
		cv.Set2D(object_points, i, 1, float(j % num_horizontal))
		cv.Set2D(object_points, i, 2, 0.0)
		i += 1
	    cv.Set1D(point_counts, successes, board_total)
	    successes += 1

    c = cv.WaitKey(15)
    if (c == ord('p')):
        c = 0
        while (c != ord('p') and c != 27):
	    c = cv.WaitKey(250)
    image = cv.QueryFrame(cam)
    cv.ShowImage("Raw Video", image)

object_points2 = cv.CreateMat(successes*board_total, 3, cv.CV_32FC1)
image_points2 = cv.CreateMat(successes*board_total, 2, cv.CV_32FC1)
point_counts2 = cv.CreateMat(successes, 1, cv.CV_32SC1)

for i in range(successes*board_total):
    cv.Set2D(image_points2, i, 0, cv.Get2D(image_points, i, 0))
    cv.Set2D(image_points2, i, 1, cv.Get2D(image_points, i, 1))
    cv.Set2D(object_points2, i, 0, cv.Get2D(object_points, i, 0))
    cv.Set2D(object_points2, i, 1, cv.Get2D(object_points, i, 1))
    cv.Set2D(object_points2, i, 2, cv.Get2D(object_points, i, 2))

for i in range(successes):
    cv.Set2D(point_counts2, i, 0, cv.Get2D(point_counts, i, 0))

cv.Set2D(intrinsic, 0, 0, 1.0)
cv.Set2D(intrinsic, 1, 1, 1.0)

rotation_vectors = cv.CreateMat(successes, 3, cv.CV_32FC1)
cv.SetZero(rotation_vectors)
translation_vectors = cv.CreateMat(successes, 3, cv.CV_32FC1)
cv.SetZero(translation_vectors)

cv.CalibrateCamera2(object_points2, image_points2, point_counts2, cv.GetSize(image), intrinsic, distortion, rotation_vectors, translation_vectors, 0)

cv.Save("Intrinsics.xml", intrinsic)
cv.Save("Distortion.xml", distortion)

intrinsic = cv.Load("Intrinsics.xml")
distortion = cv.Load("Distortion.xml")

mapx = cv.CreateImage(cv.GetSize(image), cv.IPL_DEPTH_32F, 1)
mapy = cv.CreateImage(cv.GetSize(image), cv.IPL_DEPTH_32F, 1)
cv.InitUndistortMap(intrinsic, distortion, mapx, mapy)

cv.NamedWindow("Undistort")

while(image):
    t = cv.CloneImage(image)
    cv.ShowImage("Raw Video", image)
    cv.Remap(t, image, mapx, mapy)
    cv.ShowImage("Undistort", image)

    c = cv.WaitKey(15)
    if (c == ord('p')):
	c = 0
	while (c != ord('p') and c != 27):
	    c = cv.WaitKey(250)
    if (c == 27):
	break

    image = cv.QueryFrame(cam)
