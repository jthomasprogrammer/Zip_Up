package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
	private ArrayList<File> files;
	private String zipname;

	/*
	 * Default constructor for the Zip class.
	 * @param An array of files that this class will zip.
	 * @param The name of the zip file this class will create.
	 */
	public Zip(ArrayList<File> files, String zipname){
		this.files = files;
		this.zipname = zipname;
	}

	public String zipFiles(){
		byte[] buffer = new byte[18024];
		int len;
		try {
			if(zipname.contains(".")){
				zipname = zipname.split(".")[0] + ".zip";
			}else{
				zipname = zipname + ".zip";
			}
			File outputFile = new File(zipname);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
			
			//Sets the compression.
			out.setLevel(Deflater.DEFAULT_COMPRESSION);

			//Goes through the files. 
			for (int i = 0; i < files.size(); i++) {
				//Load a new file input stream.
				FileInputStream in = new FileInputStream(files.get(i));

				// Add ZIP entry to output stream.
				String filename = files.get(i).getName();
				out.putNextEntry(new ZipEntry(filename));

				//Transfer bytes from the current file to the ZIP file.
				while ((len = in.read(buffer)) > 0){
					out.write(buffer, 0, len);
				}

				//Close the current entry.
				out.closeEntry();

				//Close input stream.
				in.close();

			}
			// Close the Zip file.
			out.close();
			return "ZipUp has been successful";
		}catch(Exception e){
			return "A exception occured during the zip process "+e;
		}
	}
}
