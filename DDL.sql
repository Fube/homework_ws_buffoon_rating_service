CREATE TABLE `ratings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `joke_guid` binary(16) NOT NULL,
  `guid` binary(16) NOT NULL,
  `opinion` smallint DEFAULT NULL,
  `user_guid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `guid_UNIQUE` (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
