import React, {PropTypes as t} from 'react';
import IceCreamRow from './ice-cream-row';

const IceCreamList = ({deleteCb, iceCreamMap}) => {
  const list = Object.keys(iceCreamMap)
    .map(id => ({id, flavor: iceCreamMap[id]}));
  list.sort((a, b) => a.flavor.localeCompare(b.flavor));

  return (
    <ul className="ice-cream-list">
      {
        list.map(iceCream =>
          <IceCreamRow
            deleteCb={deleteCb}
            id={iceCream.id}
            key={iceCream.id}
            flavor={iceCream.flavor}
          />)
      }
    </ul>
  );
};

IceCreamList.propTypes = {
  deleteCb: t.func.isRequired,
  iceCreamMap: t.object.isRequired
};

export default IceCreamList;
