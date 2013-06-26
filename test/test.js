entity.setX(100);
entity.setY(100);

function update(object, delta) {
	
	var globalX = object.getX();
	var globalY = object.getY();
	
	var amount = 10;
	
	object.setX(globalX + amount);
	object.setY(globalY + amount);
}