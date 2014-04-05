Version 1.0

To construct different GUI tests

	1) Open EventRecorder.java
	2) Update 'MY_DATA_RECORD' field to output to a certain directory. Make sure to change
	   the file output from MyRecord.ser to the specified test, such as 'test1.ser'. 
	3) Go to the main method inside EventRecorder.java and alter 
	   'new AuthoringView();' to instead construct an instance of your own view
	4) Run EventRecorder.java, which is currently has set a maximum record time of 25 seconds.
	   To extend this record time, update the MAX_RECORD_TIME field. If you want to finish the 
	   recording early, there will be a stop button on the screen that can be pressed to end the
	   recording.
	5) Currently, recorded commands include moving the mouse position and pressing/releasing either 
	   the mouse or a key. 
	
To run the GUI test
	
	1) Ensure the .ser file you want to run is specified within EventRecorder.java under MY_DATA_RECORD
	2) Run EventPlayback.java, which will playback recorded GUI events to ensure that all functionality is
	   still working
	
Updated versions will appear over time, with updated instructions in this file.

