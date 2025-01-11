import http from 'k6/http';
import { check } from 'k6';

const shortUrls = [
  '939518', 'd7b17e', 'c550ee', '46d81f', '2e7210', '526b04', '94cfcb', '23450e', 'acf6a7',
  '4b09e3', '8c9fb6', 'ba2ee1', '5129cd', '52bb47', 'bc3a0e', 'dfa586', '122f3b', '8a3333',
  'bdeccc', 'e6c5c3', 'b50ecf', '221bcd', '436579', '036ed1', '41211b', '7d5515', '2ed759',
  '1b9376', '99773f', 'e6366e', '6f5dd6', '2e321b', '685739', '2c5180', 'ac7442', '98e11a',
  '96298d', '2b0f87', '359802', '7099fc', '4a58c8', '6b8c45', '5d3521', '9a9942', '195e30',
  'a3be35', '88c94b', '34938c', '02735f', 'c9bb3a', '2caceb', '5dd273', 'e3b732', 'dc6694',
  'd703d8', 'a5a7b5', '15acff', 'ce0282', '2b7885', '423150', 'e5ce21', '3bab05', '90e8e6',
  'a7fdfb', '09c14f', '7bd6d7', '4cfb1e', 'a466be', '791256', 'f3c2c6', '475301', '6d3c46',
  '7ca711', '2460f3', '67b779', 'c6e6e6', 'f0a073', '6c0418', '7af9c6', '57ba92', 'b4b395',
  '844f5e', '259978', 'ba2316', '037b5e', 'bc68a9', '3aa37a', 'ef8b82', '851eb4', '93f243',
  'f387b5', '89dd67', 'fc610d', '756bff', '035cdf', '23960c', '3ad579', '3780d0', '4db0e1',
];

export const options = {
  scenarios: {
    constant_rps: {
      executor: 'constant-arrival-rate',
      rate: 2000,
      timeUnit: '1s',
      duration: '10s',
      preAllocatedVUs: 2500,
      maxVUs: 3000,
    },
  },
};

export default function () {
  const shortUrl = shortUrls[Math.floor(Math.random() * shortUrls.length)];
  const url = `http://localhost:8080/${shortUrl}`;

  const res = http.get(url, {
    redirects: 0,
  });

  check(res, {
    'status is 301': (r) => r.status === 301,
  });
}