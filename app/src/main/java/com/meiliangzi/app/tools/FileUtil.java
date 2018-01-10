package com.meiliangzi.app.tools;

import java.io.File;
import java.util.List;

public class FileUtil
{
	private FileUtil()
	{
	}
	
	public static void recursionFile(File cacheFile, List<File> cacheFiles)
	{
		File[] files = cacheFile.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				recursionFile(file, cacheFiles);
			} else
			{
				cacheFiles.add(file);
			}
		}
	}
}
