package com.enhance.swing.image;

import java.net.URL;

import javax.swing.*;

public class ImageFactory {

	public static ImageIcon getImage(String name) {
		URL urlImage = ImageFactory.class.getClassLoader().getResource(name);
		if (urlImage == null) {
			return null;
		}
		return new ImageIcon(urlImage);
	}

	public static URL getImageURL(String name) {
		URL urlImage = ImageFactory.class.getClassLoader().getResource(name);
		return urlImage;
	}
}