# Seam Carver

---

## Overview

This is a dynamic programming solution to the [seam
carving](http://en.wikipedia.org/wiki/Seam_carving) image resizing
algorithm. Instead of removing columns of pixels, it finds vertical
"seams" (paths of least importance) to remove in order to prevent image
distortion.

The files of interest are the following:

-   GradientFilter.java
-   SeamCarvingFilter.java
-   SeamFinder.java


## Using the Seam Carver

Download and run this [jar
file](https://github.com/downloads/kverrier/Seam-Carver/seam-carver.jar).
After running the file, a window will pop-up asking to browse to the
desired image file on your computer that you would like to resize. After
selecting the desired file, the image will appear.  Put the mouse cursor
on one of the side edges of the image and the pointer will change from a
single arrow to a double arrow.  Click and drag the image to the desired
reduced size. A loading screen will appear while the seams are being
found and removed.  Voilà, the image has been seam carved.
