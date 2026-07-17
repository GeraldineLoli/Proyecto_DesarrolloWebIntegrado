-- Datos para la tabla: usuarios
--

INSERT INTO public.usuarios (id, apellido, "contraseña", dni, email, fecha_nacimiento, fecha_registro, nombre, rol, telefono) VALUES 
(11, 'Loli', '$2a$10$jNISZB90h7g/niUTZcshNuOp9DJvc3oD0OapfakVpfnVXiSyAg6um', '12345678', 'gery@gmail.com', NULL, '2026-07-05', 'Geraldine', 'CLIENTE', NULL);

INSERT INTO public.usuarios (id, apellido, "contraseña", dni, email, fecha_nacimiento, fecha_registro, nombre, rol, telefono) VALUES 
(12, 'Sistema', '$2a$10$Nj5A/t9O9onMGe.Mf..A1evrVXkidG.jQOxXuazVAqa0EDmlLirUa', '00000000', 'admin@ticketapp.com', NULL, '2026-07-06', 'Administrador', 'ADMIN', '999999999');

INSERT INTO public.usuarios (id, apellido, "contraseña", dni, email, fecha_nacimiento, fecha_registro, nombre, rol, telefono) VALUES 
(13, 'Chumpitaz', '$2a$10$hOrNPP77pSyIJsqXjYpwXuMzkzgG0d27LT9b86ooAJTnESYc4EzYy', '98712364', 'fabi@gmail.com', NULL, '2026-07-06', 'Fabiana', 'CLIENTE', NULL);


--
-- Datos para la tabla: eventos
--

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(1, true, 'Metallica', 'CONCIERTO', 'El mejor festival de rock del año', 'Av. Principal 123', 180, 18, '2024-12-15 20:00:00', 'https://ejemplo.com/rockfest.jpg', 'Estadio Nacional', 'Rock Festival 2024');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(2, true, 'David Guetta', 'ELECTRONICA', 'Noche de música electrónica', 'Calle Falsa 456', 240, 21, '2024-11-20 22:00:00', 'https://ejemplo.com/tecno.jpg', 'Discoteca Central', 'Tecno Party');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(3, true, 'Shakespeare Company', 'TEATRO', 'Obra de teatro: Romeo y Julieta', 'Plaza Mayor 789', 120, 12, '2024-10-05 19:00:00', 'https://ejemplo.com/teatro.jpg', 'Teatro Municipal', 'Teatro Clásico');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(4, true, 'Chef Rodríguez', 'GASTRONOMIA', 'Degusta lo mejor de la cocina local', 'Av. Gastronómica 321', 480, 0, '2024-11-10 11:00:00', 'https://ejemplo.com/gastro.jpg', 'Centro de Convenciones', 'Feria Gastronómica');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(5, true, 'Elon Musk', 'CONFERENCIA', 'Innovación y tecnología', 'Av. Tecnológica 456', 480, 16, '2024-09-25 09:00:00', 'https://ejemplo.com/tech.jpg', 'Centro de Convenciones', 'Conferencia Tech 2024');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(6, true, 'Marc Anthony', 'DANZA', 'Noche de baile latino', 'Calle del Ritmo 789', 240, 18, '2024-10-20 21:00:00', 'https://ejemplo.com/salsa.jpg', 'Salón Tropical', 'Salsa Night');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(7, true, 'El Show de la Granja', 'FAMILIAR', 'Diversión para toda la familia', 'Av. Parque 111', 360, 0, '2024-12-25 10:00:00', 'https://ejemplo.com/infantil.jpg', 'Parque Central', 'Festival Infantil');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(8, true, 'Wynton Marsalis', 'JAZZ', 'Festival de jazz al aire libre', 'Av. Melodía 555', 300, 0, '2024-11-05 16:00:00', 'https://ejemplo.com/jazz.jpg', 'Parque de la Música', 'Jazz en el Parque');

INSERT INTO public.eventos (id, activo, artista_principal, categoria, descripcion, direccion, duracion_minutos, edad_minima, fecha_hora, imagen_url, lugar, nombre) VALUES 
(10, true, 'Artista Principal 1', 'CONCIERTO', 'El mejor concierto del año', 'Av. Los Shyris 1234', 180, 18, '2025-06-15 20:00:00', 'https://ejemplo.com/imagen.jpg', 'Estadio Monumental', 'Nuevo Concierto 2026');


--
-- Datos para la tabla: zonas
--

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(1, 500, '#FFD700', 500, 1, 'VIP', 250, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(2, 2000, '#87CEEB', 2000, 1, 'PLATEA', 150, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(3, 5000, '#90EE90', 5000, 1, 'GENERAL', 80, false);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(4, 200, '#FFD700', 200, 2, 'VIP', 180, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(5, 800, '#FF69B4', 800, 2, 'PISTA', 100, false);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(6, 500, '#ADD8E6', 500, 2, 'CABINA', 60, false);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(7, 100, '#FFD700', 100, 3, 'PALCO', 120, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(8, 300, '#F0E68C', 300, 3, 'PLATEA', 80, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(9, 400, '#D3D3D3', 400, 3, 'GENERAL', 45, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(10, 200, '#FFD700', 200, 4, 'VIP DEGUSTACIÓN', 200, false);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(11, 3000, '#98FB98', 3000, 4, 'GENERAL', 80, false);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(12, 100, '#FFD700', 100, 5, 'FRONT ROW', 350, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(13, 500, '#C0C0C0', 500, 5, 'PREFERENCIAL', 200, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(14, 1000, '#F5F5DC', 1000, 5, 'GENERAL', 120, true);

INSERT INTO public.zonas (id, capacidad_total, color_mapa, entradas_disponibles, evento_id, nombre, precio, tiene_numeracion) VALUES 
(16, 100, '#4f6ef7', 100, 2, 'GENERAL', 30, false);


--
-- Datos para la tabla: pedidos
--

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(1, 'ORD-ABC123', 'PAGADO', '2024-06-01 10:30:00', 300, 2);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(2, NULL, 'PAGADO', '2024-06-15 15:20:00', 300, 3);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(3, 'ORD-GHI789', 'PENDIENTE', '2024-07-01 11:45:00', 500, 3);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(4, 'ORD-JKL012', 'PAGADO', '2024-08-10 09:15:00', 250, 3);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(5, 'ORD-MNO345', 'PAGADO', '2024-09-05 14:30:00', 180, 4);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(7, 'ORD-D76A5CA7', 'PENDIENTE', '2026-05-25 19:54:23.673733', 150, 4);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(8, 'ORD-96EFB552', 'PENDIENTE', '2026-07-05 22:05:17.058795', 250, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(9, 'ORD-60760198', 'PENDIENTE', '2026-07-05 22:06:57.925207', 250, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(10, 'ORD-55B96F39', 'PENDIENTE', '2026-07-05 22:09:13.920057', 180, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(11, 'ORD-69AD77FF', 'PENDIENTE', '2026-07-05 22:12:10.200426', 100, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(12, 'ORD-2325D25F', 'PAGADO', '2026-07-05 22:21:17.652455', 750, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(13, 'ORD-7447A8E1', 'PAGADO', '2026-07-06 20:56:48.930871', 250, 11);

INSERT INTO public.pedidos (id, codigo_pedido, estado, fecha_creacion, total, usuario_id) VALUES 
(14, 'ORD-E1D59B5F', 'PAGADO', '2026-07-06 21:40:17.069672', 750, 11);


--
-- Datos para la tabla: entradas
--

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(6, 'TKT-83579146', NULL, 'PAGADA', 1, '2026-07-06 03:21:18.027', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(8, 'TKT-A48F0140', NULL, 'PAGADA', 1, '2026-07-06 03:21:17.991', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(7, 'TKT-5BAE03A6', NULL, 'PAGADA', 1, '2026-07-06 03:21:18.027', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(9, 'TKT-225383D7', NULL, 'PAGADA', 1, '2026-07-07 01:56:49.254', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(10, 'TKT-886AE451', NULL, 'PAGADA', 1, '2026-07-07 02:40:17.391', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(11, 'TKT-BAA787E6', NULL, 'PAGADA', 1, '2026-07-07 02:40:17.392', NULL, 'VISA', 0, 250, 11, 1);

INSERT INTO public.entradas (id, codigo_entrada, codigo_transaccion, estado, evento_id, fecha_compra, fila, metodo_pago, numero_asiento, precio_pagado, usuario_id, zona_id) VALUES 
(12, 'TKT-7FFC43CC', NULL, 'PAGADA', 1, '2026-07-07 02:40:17.392', NULL, 'VISA', 0, 250, 11, 1);


--
-- Datos para la tabla: pagos
--

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(1, 'PAY-001', NULL, 'COMPLETADO', '2024-06-01 10:31:00', 'TARJETA', 300, NULL, '****1234', 1, 2);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(2, 'PAY-002', NULL, 'COMPLETADO', '2024-06-15 15:21:00', 'TARJETA', 150, NULL, '****5678', 2, 2);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(3, 'PAY-003', NULL, 'PENDIENTE', '2024-07-01 11:46:00', 'YAPE', 500, NULL, NULL, 3, 3);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(4, 'PAY-004', NULL, 'COMPLETADO', '2024-09-05 14:32:00', 'PLIN', 250, NULL, NULL, 4, 4);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(5, 'PAY-005', NULL, 'PENDIENTE', '2024-10-01 16:05:00', 'EFECTIVO', 400, NULL, NULL, 5, 5);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(6, 'TXN-6CCBA9AE', NULL, 'COMPLETADO', '2026-05-25 20:29:01.604593', 'TARJETA', 500, NULL, NULL, 3, 3);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(7, 'TXN-632F826F', NULL, 'COMPLETADO', '2026-07-05 22:05:18.234964', 'VISA', 250, NULL, '****2345', 8, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(8, 'TXN-65299CC9', NULL, 'COMPLETADO', '2026-07-05 22:06:58.078198', 'VISA', 250, NULL, '****2345', 9, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(9, 'TXN-DA6E6DD2', NULL, 'COMPLETADO', '2026-07-05 22:09:14.074501', 'VISA', 180, NULL, '****3456', 10, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(10, 'TXN-C3135E9C', NULL, 'COMPLETADO', '2026-07-05 22:12:10.375023', 'MASTERCARD', 100, NULL, '****7654', 11, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(11, 'TXN-4E3C6C20', NULL, 'COMPLETADO', '2026-07-05 22:21:17.961878', 'VISA', 750, NULL, '****6543', 12, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(12, 'TXN-F736538A', NULL, 'COMPLETADO', '2026-07-06 20:56:49.207742', 'VISA', 250, NULL, '****8765', 13, 11);

INSERT INTO public.pagos (id, codigo_transaccion, comprobante_url, estado, fecha_pago, metodo_pago, monto, notas, numero_tarjeta, pedido_id, usuario_id) VALUES 
(13, 'TXN-C8CBF8F4', NULL, 'COMPLETADO', '2026-07-06 21:40:17.314803', 'VISA', 750, NULL, '****3456', 14, 11);


--
-- Datos para la tabla: promociones
--

INSERT INTO public.promociones (id, activo, cantidad_disponible, cantidad_usada, codigo, descripcion, evento_id, fecha_fin, fecha_inicio, porcentaje_descuento) VALUES 
(1, true, 100, 0, 'ROCK20', '20% descuento Rock Festival', 1, '2024-12-14 23:59:59', '2024-11-01 00:00:00', 20);

INSERT INTO public.promociones (id, activo, cantidad_disponible, cantidad_usada, codigo, descripcion, evento_id, fecha_fin, fecha_inicio, porcentaje_descuento) VALUES 
(2, true, 200, 50, 'TECNO15', '15% descuento Tecno Party', 2, '2024-11-19 23:59:59', '2024-10-01 00:00:00', 15);

INSERT INTO public.promociones (id, activo, cantidad_disponible, cantidad_usada, codigo, descripcion, evento_id, fecha_fin, fecha_inicio, porcentaje_descuento) VALUES 
(3, true, 50, 25, 'TEATRO10', '10% descuento Teatro', 3, '2024-10-04 23:59:59', '2024-09-01 00:00:00', 10);

INSERT INTO public.promociones (id, activo, cantidad_disponible, cantidad_usada, codigo, descripcion, evento_id, fecha_fin, fecha_inicio, porcentaje_descuento) VALUES 
(4, true, 300, 0, 'GASTRO25', '25% descuento Feria Gastronómica', 4, '2024-11-09 23:59:59', '2024-10-01 00:00:00', 25);

INSERT INTO public.promociones (id, activo, cantidad_disponible, cantidad_usada, codigo, descripcion, evento_id, fecha_fin, fecha_inicio, porcentaje_descuento) VALUES 
(5, true, 150, 120, 'TECH30', '30% descuento Conferencia Tech', 5, '2024-09-24 23:59:59', '2024-08-01 00:00:00', 30);


--
-- Datos para la tabla: resenas
--

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(1, 5, 'Excelente evento, sonido increíble, volvería sin dudas', 1, 1, '2024-06-05', 2);

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(2, 4, 'Muy buen concierto, el artista principal excelente', 2, 1, '2024-06-20', 2);

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(3, 5, 'La mejor noche de mi vida, la música espectacular', 3, 2, '2024-07-10', 3);

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(4, 3, 'Buena obra pero un poco larga', 4, 3, '2024-09-10', 4);

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(5, 5, 'Conferencia muy instructiva, aprendí mucho', 5, 5, '2024-10-05', 5);

INSERT INTO public.resenas (id, calificacion, comentario, entrada_id, evento_id, fecha, usuario_id) VALUES 
(7, 4, 'Buen evento', 6, 1, '2026-07-06', 11);


--
-- Datos para la tabla: evento_artistas_invitados
--

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(1, 'Foo Fighters');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(1, 'Nirvana');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(1, 'Pearl Jam');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(2, 'Tiësto');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(2, 'Armin van Buuren');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(3, 'Actores Locales');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(5, 'Bill Gates');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(5, 'Steve Jobs');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(10, 'Invitado1');

INSERT INTO public.evento_artistas_invitados (evento_id, artista) VALUES 
(10, 'Invitado2');


--
-- Datos para la tabla: pedido_entradas
--

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(1, 1);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(3, 3);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(4, 4);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(5, 5);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(2, 1);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(2, 2);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(2, 3);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(12, 6);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(12, 8);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(12, 7);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(13, 9);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(14, 10);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(14, 11);

INSERT INTO public.pedido_entradas (pedido_id, entrada_id) VALUES 
(14, 12);
