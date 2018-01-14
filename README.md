# FaceEmoji
FaceEmoji an android app which turns facial expression into an appropriate emoticons using Google Mobile Vision API's FaceDetection Library.

This app reads classification landmarks from the persons face and using probability thresholds this app will set emoji on the face. 

This app supports 8 different facial expression and detect facial landmarks on different angles

The user can also save, share and delete the image.

## Design Flow
1. The user opens an app and launches the camera and take a picture.
2. Thr Google Mobile Vision API's Face Detection Library process the pic to extract the classification data and map that data to a closely matching emoji and overly that emoji onto face detected location

![atp txt](https://github.com/shahshail/FaceEmoji/blob/master/Screenshots/face_emoji.png)

3. The user can finally shave that image to phone or sd card storage, delete iit from cache memory or share that image.

## Face Detection Threshold and Probabilities
This app receives Probability of each expression from Face Detection library which is lies between 0 to 1.(For an example 0 probability means eye is close and 1 probability means eye is completely open).

Face Detection Library gives three classifications
**1.** Smiling Probability
**2.** Left Eye open probability
**3.** Right Eye Open Probability
 
In this app i have defined three Threshold constants
**1.** EMOJI_SCALE_FACTOR = **0.09**
**2.** SMILING_PROB_THRESHOLD = **.15**
**3.** EYE_OPEN_PROB_THRESHOLD = **0.05**

So with the use of above three probabilities and thresholds we can calculate different 8 expressions.

**1.** Smile

**2.** Frown

**3.** Left Wink

**4.** Right Wink

**5.** Left Wink Frown

**6.** Right Wink Frown

**7.** Closed Eye Smile

**8.** Closed Eye Frown

![atp txt](https://github.com/shahshail/FaceEmoji/blob/master/Screenshots/thresholds.png)


### Landmarks
Landmarks are various part of human face. i.e. Eye, Botton of the mouth, Base of the nose and lips.

Landmarks are used to identify three activities.
- Blinking Acivity
- Smiling Activity 
- Eye Open-Closed Activity

Face doesnt has to be directly facing to the camera the app is smart enaugh to detect the face in different angles and inclinations.

## Storage and Resample pic methods
**resamplePic()**: The resamplePic() method gets the height and width of the device screen in pixels, and resamples the passed in image to fit the screen.

**createTempImageFile()**: This method creates a temporary file in the external cache directory and returns the new temp file.

**deleteImageFile()**: This method attempts to delete the image at the passed in path. If it fails, it shows a toast.

**galleryAddPic()**: This method cause the image content provider to add the image from the passed in path to the system gallery, so it can be found by other app. It is only called inside the saveImage() described below.

**saveImage()**: This method saves the passed in Bitmap in the External Storage, in the subdirectory called "Emojify". It also adds the image to the system gallery by calling the above galleryAddPic().

**shareImage()**: This method creates a share implicit intent, which will bring up the system chooser with apps that handle sharing an image.


## Screenshots:
![atp txt](https://github.com/shahshail/FaceEmoji/blob/master/Screenshots/screenshot.png)

