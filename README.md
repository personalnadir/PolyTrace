
# PolyTrace: What is this?

PolyTrace is allows users to create 2D polygons and export them as JSON data. You can load images and trace polygons around them or just work from scratch. PolyTrace will also identify whether a polygon is convex or concave. But it will export all types!

# GUI and Shortcuts (Mac Only)
![Load Existing Polygon Definition](data/folder.png)
Load an existing polygon trace (will load the image associated with the trace as well). Any unsaved changes will be lost. _(Cmd+o)_  
![Save Current Polygon Definition](data/save.png)
Save the current polygon trace (will include a reference to the current image file). _(Cmd+s)_  
![Select Image to Trace](data/picture.png)
Opens an image to trace. Any unsaved changes will be lost. _(i)_  
![Start New Polygon](data/polygon.png)
Starts a new polygon. _(p)_  
![Zoom In](data/zoomin.png)
Zoom in on the centre of the screen.  
![Zoom Out](data/zoomout.png)
Zoom out of centre of the screen.  

# Instructions

The first step is always to load an image to trace, you can do that by clicking the icon (see above or press 'i'). Once the image is selected you can start tracing a polygon.

## Add point to polygon
To add a point to the polygon, click on the place on the screen where you want to add the point.

## To delete a point from the polygon
You can delete the current selected point, which by default is the last point added, by hitting the backspace key.

## Selecting a point
You can select a point on a polygon by clicking on it. New points will be added after this point on the polygon.

## Changing a point
You can move a point by dragging it with the mouse.

## Starting a new polygon
Press 'p' to start adding points to a new polygon. You can select other polygons by clicking on a point on them.

## Convex vs. Concave Polygons
PolyTrace will render concave polygons with a red fill colour. Convex polygons will have a blue fill colour. Concave polygons will still be exported, and it will depend on your application to handle them.

## Polygon Winding
PolyTrace makes no attempt to order the winding of the polygons produced. The verticies will be exported in the order described in the polygon. 

## Images
At the moment PolyTrace does not save images traced, it only stores a reference to the file location. If the file is moved PolyTrace will not be able to load it.