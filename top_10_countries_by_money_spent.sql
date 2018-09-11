select sum(e.productPrice) country_spent, t.country_name
from events tablesample(50000 rows) e, (
  select distinct(netToStruct(cb.network).mask) mask
  from country_blocks cb) m
join (select cb.network, netToStruct(network) net, cl.country_name
  from country_locations cl
  join country_blocks cb on cb.geoname_id = cl.geoname_id) as t
  on m.mask = t.net.mask and ipToInt(e.clientip) & m.mask = t.net.net & t.net.mask
group by t.country_name
order by country_spent desc
limit 10

--  3000  56
--  10000 1:05
--  20000 1:20
--  33724 1:25
--  50000 1:42