package edu.cmu.cs211.seamcarving;

/**
 * A filter object that calculate the magnitude of the gradient of a GrayImage.
 * 
 * The magnitude of the gradient at a point is defined as:
 * 
 * g(i,j) = sqrt( ( p(i,j+1)-p(i,j) ) ^2 + ( p(i+1,j)-p(i,j) ) ^2 )
 * 
 * where p(i,j) is the value of the pixel at (i,j)
 * 
 * Any point outside of the frame is considered to have a pixel value 0
 *
 * @author Kyle Verrier
 */
public class GradientFilter {

	public GradientFilter() {

	}

	/**
	 * Apply filter to the image
	 * 
	 * @param image
	 *            Image to filter
	 * @return A resulting image of the gradient magnitude
	 */
	public GrayImage filter(GrayImage image) {
		if (image.getWidth() == 0)
			return image;

		// initializes filter with all 0's.
		float[][] filter = new float[image.getHeight()][image.getWidth()];

		setUpFilter(image, filter);

		return new GrayImage(filter); // returns filter as GrayImage
	}

	/**
	 * Creates a filter of gradients using a passed in image and an initalized
	 * filter with all 0's. Goes through all of the pixels of the GrayImage.
	 * 
	 * @param image
	 * @param filter
	 */
	private static void setUpFilter(GrayImage image, float[][] filter) {
		for (int i = 0; i < filter.length; i++) {
			for (int j = 0; j < filter[0].length; j++) {
				filter[i][j] = calcGradient(image, i, j);
			}
		}
	}

	/**
	 * Calculates the gradient of a given cell (i,j) of a passed in image. The
	 * formula is according to:
	 * 
	 * g(i,j) = sqrt( ( p(i,j+1)-p(i,j) ) ^2 + ( p(i+1,j)-p(i,j) ) ^2 )
	 * 
	 * If i+1 or j+1 is not in the image, it is considered the value of 0.
	 * 
	 * @param image
	 * @param i
	 * @param j
	 * @return
	 */
	private static float calcGradient(GrayImage image, int i, int j) {

		int height = image.getHeight();
		int width = image.getWidth();

		float point = image.get(i, j);
		float p1, p2 = 0;

		if (i == height - 1 && j == width - 1) {
			p1 = (float) Math.pow(point, 2);
			p2 = (float) Math.pow(point, 2);
			return (float) Math.sqrt(p1 + p2);
		}

		if (i == height - 1) {
			p1 = (float) Math.pow((image.get(i, j + 1) - point), 2);
			p2 = (float) Math.pow(point, 2);
			return (float) Math.sqrt(p1 + p2);
		}

		if (j == width - 1) {
			p1 = (float) Math.pow(point, 2);
			p2 = (float) Math.pow((image.get(i + 1, j) - point), 2);
			return (float) Math.sqrt(p1 + p2);
		}

		if (j == width - 1) {
			p1 = (float) Math.pow(point, 2);
			p2 = (float) Math.pow((image.get(i + 1, j) - point), 2);
			return (float) Math.sqrt(p1 + p2);
		}

		p1 = (float) Math.pow((image.get(i, j + 1) - point), 2);
		p2 = (float) Math.pow((image.get(i + 1, j) - point), 2);

		return (float) Math.sqrt(p1 + p2);
	}

}
