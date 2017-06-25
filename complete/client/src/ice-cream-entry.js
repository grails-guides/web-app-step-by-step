import React, {PropTypes as t} from 'react';

const IceCreamEntry = ({addCb, changeCb, flavor}) =>
  <form
    className="ice-cream-entry"
    onSubmit={event => event.preventDefault()}
  >
    <label>Flavor</label>
    <input type="text" autoFocus onChange={changeCb} value={flavor} />
    {/* using unicode heavy plus for button */}
    <button onClick={() => addCb(flavor)}>&#x2795;</button>
  </form>;

IceCreamEntry.propTypes = {
  addCb: t.func.isRequired,
  changeCb: t.func.isRequired,
  flavor: t.string.isRequired,
};

export default IceCreamEntry;
