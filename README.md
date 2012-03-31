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
After running the file, a window will pop-up asking to browse to an
image file. After selecting a file, it will give a preview of the image.
Put the mouse on one of the side edges of the image and the mouse will
change shape. Click and drag the image to the desired size and then
there should be a loading screen while the seams are being found and
removed.



