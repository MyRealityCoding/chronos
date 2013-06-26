entity.setX(100.0);
entity.setY(100.0);

function update(object, delta) {
	
	var globalX = object.getX();
	var globalY = object.getY();
	
	var amount = 10;
	
	object.setX(globalX + amount);
	object.setY(globalY + amount);
}