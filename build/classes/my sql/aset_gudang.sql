-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 24 Bulan Mei 2023 pada 04.18
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aset_gudang`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `history`
--

CREATE TABLE `history` (
  `waktu` datetime NOT NULL DEFAULT current_timestamp(),
  `info` varchar(100) NOT NULL,
  `ket` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `history`
--

INSERT INTO `history` (`waktu`, `info`, `ket`) VALUES
('2023-05-20 18:38:57', 'Updating inventory for product paku aku id XF34852, 10 Pcs paku akuadded to stock', 'ADD'),
('2023-05-20 22:14:05', 'Update alung product data (SH-48299)', 'EDIT'),
('2023-05-20 22:19:59', 'Updating inventory for product paku aku id XF34852, 50 Pcs paku aku take to stock', 'OUT'),
('2023-05-20 22:20:34', 'Delete product data \'2\' (QV-91942)', 'DELETE'),
('2023-05-21 22:54:58', 'Updating inventory for product paku aku id XF34852, 30 Pcs paku aku added to stock', 'ADD'),
('2023-05-21 22:55:24', 'Updating inventory for product padang id IO-32020, 40 Box padang added to stock', 'ADD'),
('2023-05-21 22:55:45', 'Updating inventory for product adsad id CY79525, 20 Pcs adsad take to stock', 'OUT');

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id` varchar(11) NOT NULL,
  `nama_produk` varchar(20) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `stok_awal` int(11) NOT NULL,
  `harga_beli` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `masuk` int(11) DEFAULT 0,
  `keluar` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id`, `nama_produk`, `satuan`, `stok_awal`, `harga_beli`, `harga_jual`, `masuk`, `keluar`) VALUES
('CY79525', 'adsad', 'Pcs', 12, 21213, 3123, 28, 20),
('DE-28149', 'CAT', 'Pcs', 12, 210, 222, 10, 0),
('IO-32020', 'padang', 'Box', 3, 10000, 15000, 40, 0),
('SH-48299', 'alung', 'Pcs', 1, 1, 1, 29, 0),
('UM-20946', 'tisu', 'Roll', 22, 32, 323, 11, 12),
('WU-33689', 'nol', 'Pcs', 40, 10200, 13000, 200, 40),
('XF34852', 'paku aku', 'Pcs', 122, 1100, 1200, 103, 175);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `nama` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `nama`) VALUES
(1, 'admin', 'admin', 'admin'),
(2, 'refanda', 'refanda', 'refanda');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`waktu`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
