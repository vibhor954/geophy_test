package listener;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import utils.Data;
import utils.DataElements;
import utils.DataReader;

public class AnnotationTransformer extends Data implements
		IAnnotationTransformer {
	DataReader db = new DataReader();

	@Override
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {

		try {
			db.setupDataSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Data> list = DataReader.getDataObjectRepo();
		Iterator<Data> itr = list.iterator();
		while (itr.hasNext()) {
			Map<String, DataElements> dataMap = itr.next().getElementList();
			for (Map.Entry<String, DataElements> entry : dataMap.entrySet()) {
				if (entry.getValue().getTestMethodName().equalsIgnoreCase(testMethod.getName())
						&& entry.getValue().getRunStatus().equalsIgnoreCase("skip")) {
					annotation.setEnabled(false);
				}
			}
		}

		annotation.setRetryAnalyzer(Retry.class);

	}

}