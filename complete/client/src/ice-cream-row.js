import React, {PropTypes as t} from 'react';

const IceCreamRow = ({deleteCb, flavor, id}) =>
  <li className="ice-cream-row">
    {/* using unicode heavy x for button */}
    <button onClick={() => deleteCb(id)}>&#x2716;</button>
    {flavor}
  </li>;

IceCreamRow.propTypes = {
  deleteCb: t.func.isRequired,
  flavor: t.string,
  id: t.string
};

export default IceCreamRow;
