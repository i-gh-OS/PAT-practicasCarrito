-- Limpieza (si ya existiera algo)
DELETE FROM linea_carrito;
DELETE FROM carrito;

-- Carritos
INSERT INTO carrito (id_carrito, id_usuario, correo, total_precio)
VALUES (1, 1001, 'irene@example.com', 0);

INSERT INTO carrito (id_carrito, id_usuario, correo, total_precio)
VALUES (2, 1002, 'carlos@example.com', 0);

-- Líneas del carrito 1
INSERT INTO linea_carrito (id_linea, id_carrito, id_articulo, precio_unitario, unidades, coste_linea)
VALUES (10, 1, 501, 9.99, 2, 19.98);

INSERT INTO linea_carrito (id_linea, id_carrito, id_articulo, precio_unitario, unidades, coste_linea)
VALUES (11, 1, 502, 5.50, 1, 5.50);

-- Total del carrito 1 (si lo guardas persistido)
UPDATE carrito
SET total_precio = 25.48
WHERE id_carrito = 1;

-- Líneas del carrito 2
INSERT INTO linea_carrito (id_linea, id_carrito, id_articulo, precio_unitario, unidades, coste_linea)
VALUES (20, 2, 700, 12.00, 3, 36.00);

UPDATE carrito
SET total_precio = 36.00
WHERE id_carrito = 2;