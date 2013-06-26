object.setX(100.0);
object.setY(100.0);

function update(object, delta) {
	
	var globalX = object.getX();
	var globalY = object.getY();
	
	var amount = delta;
	
	object.setX(globalX + amount);
	object.setY(globalY + amount);
}